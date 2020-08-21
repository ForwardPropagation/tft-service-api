package com.fp.tft.provider.summoner;

import com.fp.tft.Constants;
import com.fp.tft.provider.TFTServiceConfig;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class SummonerProvider {

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
        ResponseEntity<SummonerV4SummonerDTO> response = null;

        try {
            response = restTemplate.exchange(
                    getSummonerNameUrl(summonerName),
                    HttpMethod.GET,
                    new HttpEntity<>(getHttpHeaders()),
                    SummonerV4SummonerDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return response.getBody();
    }

    private String getSummonerNameUrl(String summonerName) {
        return UriComponentsBuilder.fromHttpUrl(summonerServiceConfig.getBaseUri())
                .pathSegment("by-name", summonerName)
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
