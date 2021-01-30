package com.fp.tft.provider.match;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration for TFT Match downstream service
 */
@Data
@Component
@ConfigurationProperties("com.fp.tft.downstream.match")
public class MatchServiceConfig {

    private String baseUri;

    private int readTimeout;

    private int connectTimeout;
}
