package com.fp.tft.provider.summoner;

import com.fp.tft.Constants;
import com.fp.tft.provider.TFTServiceConfig;
import com.fp.tft.util.WebClientConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@AllArgsConstructor
@Slf4j
public class SummonerWebClientConfig {

    private final TFTServiceConfig tftServiceConfig;

    private final SummonerServiceConfig summonerServiceConfig;

    private final WebClientConfigService webClientConfigService;

    @Bean
    @Qualifier("SummonerProvider")
    public WebClient getSummonerProviderWebClient() {
        return WebClient.builder()
                .clientConnector(webClientConfigService.getClientHttpConnector(
                        summonerServiceConfig.getConnectTimeout(), summonerServiceConfig.getReadTimeout()))
                .baseUrl(summonerServiceConfig.getBaseUri())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(Constants.RIOT_API_KEY, tftServiceConfig.getApiKey())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Sending TFT Summoner Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("TFT Summoner Response Status Code: {}", clientResponse.statusCode().toString());
            if (clientResponse.statusCode().is5xxServerError() || clientResponse.statusCode().is4xxClientError()) {
                return Mono.error(new SummonerServiceException(clientResponse.statusCode().toString()));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
