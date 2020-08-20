package com.fp.tft.provider.summoner;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration for TFT Summoner downstream service
 */
@Data
@Component
@ConfigurationProperties("com.fp.tft.downstream.summoner")
public class SummonerServiceConfig {

    private String baseUri;

    private int readTimeout;

    private int connectTimeout;
}
