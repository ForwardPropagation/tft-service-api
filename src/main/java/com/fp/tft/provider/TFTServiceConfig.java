package com.fp.tft.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration for TFT downstream services
 */
@Data
@Component
@ConfigurationProperties("com.fp.tft.downstream")
public class TFTServiceConfig {

    private String apiKey;
}
