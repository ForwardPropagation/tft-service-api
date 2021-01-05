package com.fp.tft.provider.summoner;

import com.fp.tft.Constants;
import com.fp.tft.provider.TFTServiceConfig;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class SummonerProvider {

    public static String BY_NAME_PATH = "by-name";

    private final RestTemplate restTemplate;

    private final TFTServiceConfig tftServiceConfig;

    private final SummonerServiceConfig summonerServiceConfig;

    public SummonerProvider(@Qualifier("SummonerProvider") RestTemplate restTemplate,
                            TFTServiceConfig tftServiceConfig, SummonerServiceConfig summonerServiceConfig) {
        this.restTemplate = restTemplate;
        this.tftServiceConfig = tftServiceConfig;
        this.summonerServiceConfig = summonerServiceConfig;
    }

    public SummonerV4SummonerDTO getSummonerByName(String summonerName) {
        ResponseEntity<SummonerV4SummonerDTO> response;

        try {
            response = restTemplate.exchange(
                    getSummonerNameUrl(summonerName),
                    HttpMethod.GET,
                    new HttpEntity<>(getHttpHeaders()),
                    SummonerV4SummonerDTO.class);

        } catch (RestClientResponseException e) {
            log.debug("Summoner Service Error with Status Code {} and Response: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("Error calling TFT Summoner Service with Status Code: {}", e.getRawStatusCode());
            throw new SummonerServiceException("Error calling TFT Summoner Service!", e);
        } catch (Exception e) {
            throw new SummonerServiceException("Something went wrong when calling TFT Summoner Service", e);
        }

        return response.getBody();
    }

    private String getSummonerNameUrl(String summonerName) {
        return UriComponentsBuilder.fromHttpUrl(summonerServiceConfig.getBaseUri())
                .pathSegment(BY_NAME_PATH, summonerName)
                .build()
                .toUriString();
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(Constants.RIOT_API_KEY, tftServiceConfig.getApiKey());
        return headers;
    }
}
