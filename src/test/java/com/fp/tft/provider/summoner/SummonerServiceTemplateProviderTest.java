package com.fp.tft.provider.summoner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummonerServiceTemplateProviderTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private SummonerServiceConfig summonerServiceConfig;

    @InjectMocks
    private SummonerServiceTemplateProvider objectToTest;

    @Test
    void getSummonerServiceRestTemplate() {

        // Arrange
        when(summonerServiceConfig.getBaseUri()).thenReturn("");
        when(summonerServiceConfig.getReadTimeout()).thenReturn(1000);
        when(summonerServiceConfig.getConnectTimeout()).thenReturn(1000);
        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(new RestTemplateBuilder());

        // Act
        RestTemplate res = objectToTest.getSummonerServiceRestTemplate();

        // Assert
        assertNotNull(res);
    }
}
