package com.fp.tft.provider.summoner;

import com.fp.tft.exception.ResourceNotFoundException;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class SummonerProvider {

    public static final String BY_NAME_PATH = "by-name";
    public static final String BY_PUUID_PATH = "by-puuid";

    @Qualifier("SummonerProvider")
    private final RestTemplate restTemplate;

    public SummonerV4SummonerDTO getSummonerByName(String summonerName) {
        return getSummoner(getSummonerNameUrl(summonerName));
    }

    public SummonerV4SummonerDTO getSummonerByPuuid(String puuid) {
        return getSummoner(getSummonerPuuidUrl(puuid));
    }

    private SummonerV4SummonerDTO getSummoner(String url) {
        ResponseEntity<SummonerV4SummonerDTO> response;

        try {
            response = restTemplate.getForEntity(url, SummonerV4SummonerDTO.class);
        } catch (RestClientResponseException e) {
            log.debug("Summoner Service Error with Status Code {} and Response: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("Error calling TFT Summoner Service with Status Code: {}", e.getRawStatusCode());

            if (HttpStatus.NOT_FOUND.value() == e.getRawStatusCode())
                throw new ResourceNotFoundException("Error calling TFT Summoner Service - Summoner not found!", e);

            throw new SummonerServiceException("Error calling TFT Summoner Service!", e);
        } catch (Exception e) {
            throw new SummonerServiceException("Something went wrong when calling TFT Summoner Service", e);
        }

        return response.getBody();
    }

    private String getSummonerNameUrl(String summonerName) {
        return UriComponentsBuilder.newInstance()
                .pathSegment(BY_NAME_PATH, summonerName)
                .build()
                .toUriString();
    }

    private String getSummonerPuuidUrl(String puuid) {
        return UriComponentsBuilder.newInstance()
                .pathSegment(BY_PUUID_PATH, puuid)
                .build()
                .toUriString();
    }
}
