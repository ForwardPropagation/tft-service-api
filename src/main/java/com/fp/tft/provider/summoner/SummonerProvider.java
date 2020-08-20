package com.fp.tft.provider.summoner;

import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SummonerProvider {

    public static final String SUMMONER_BY_NAME = "by-name/{summonerName}";

    @Qualifier("SummonerProvider")
    @NonNull
    private final WebClient webClient;

    public Mono<SummonerV4SummonerDTO> getSummonerByName(final String summonerName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SUMMONER_BY_NAME)
                        .build(summonerName))
                .retrieve()
                .bodyToMono(SummonerV4SummonerDTO.class);
    }
}
