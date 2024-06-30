package com.fp.tft.provider.match;

import com.fp.tft.provider.TFTServiceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class MatchServiceTemplateProvider {

    private final RestTemplateBuilder builder;

    private final TFTServiceConfig tftServiceConfig;

    private final MatchServiceConfig config;

    @Bean("MatchProvider")
    public RestTemplate getMatchServiceRestTemplate() {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(config.getBaseUri()))
                .defaultHeader(TFTServiceConfig.RIOT_API_KEY, tftServiceConfig.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setConnectTimeout(Duration.ofMillis(config.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(config.getReadTimeout()))
                .build();
    }
}
