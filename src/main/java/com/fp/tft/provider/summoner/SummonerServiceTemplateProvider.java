package com.fp.tft.provider.summoner;

import com.fp.tft.provider.TFTServiceConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class SummonerServiceTemplateProvider {

    private final RestTemplateBuilder builder;

    private final TFTServiceConfig tftServiceConfig;

    private final SummonerServiceConfig config;

    @Bean("SummonerProvider")
    public RestTemplate getSummonerServiceRestTemplate() {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(config.getBaseUri()))
                .defaultHeader(TFTServiceConfig.RIOT_API_KEY, tftServiceConfig.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setConnectTimeout(Duration.ofMillis(config.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(config.getReadTimeout()))
                .build();
    }
}
