package com.fp.tft.provider.match;

import com.fp.tft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchProvider {

    public static final String BY_PUUID_PATH = "by-puuid";

    public static final String ID_PATH = "ids";

    public static final String COUNT_QUERY_PARAM = "count";

    private static final int DEFAULT_COUNT = 10;

    @Qualifier("MatchProvider")
    private final RestTemplate restTemplate;

    public List<String> getMatchIdListByPuuid(String puuid, Integer count) {
        ResponseEntity<List<String>> response;

        try {
            response = restTemplate.exchange(
                    getMatchUrl(puuid, count),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<String>>() {});
        } catch (RestClientResponseException e) {
            log.debug("Match Service Error with Status Code {} and Response: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("Error calling TFT Match Service with Status Code: {}", e.getRawStatusCode());

            if (HttpStatus.NOT_FOUND.value() == e.getRawStatusCode())
                throw new ResourceNotFoundException("Error calling TFT Match Service - Summoner Matches not found!", e);

            throw new MatchServiceException("Error calling TFT Match Service!", e);
        } catch (Exception e) {
            throw new MatchServiceException("Something went wrong when calling TFT Match Service", e);
        }

        return response.getBody();
    }

    private String getMatchUrl(String puuid, Integer count) {
        return UriComponentsBuilder.newInstance()
                .pathSegment(BY_PUUID_PATH, puuid)
                .path(ID_PATH)
                .queryParam(COUNT_QUERY_PARAM, count != null ? count : DEFAULT_COUNT)
                .build()
                .toUriString();
    }
}
