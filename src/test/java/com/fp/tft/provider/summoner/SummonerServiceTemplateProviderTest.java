package com.fp.tft.provider.summoner;

import com.fp.tft.provider.TFTServiceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SummonerServiceTemplateProviderTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private TFTServiceConfig tftServiceConfig;

    @Mock
    private SummonerServiceConfig summonerServiceConfig;

    @InjectMocks
    private SummonerServiceTemplateProvider objectToTest;

    @Test
    void getSummonerServiceRestTemplate() {

        // Arrange
        when(tftServiceConfig.getApiKey()).thenReturn("key");
        when(summonerServiceConfig.getBaseUri()).thenReturn("");
        when(summonerServiceConfig.getReadTimeout()).thenReturn(1000);
        when(summonerServiceConfig.getConnectTimeout()).thenReturn(1000);
        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(new RestTemplateBuilder());

        // Act
        RestTemplate res = objectToTest.getSummonerServiceRestTemplate();

        // Assert
        assertNotNull(res);

        verify(tftServiceConfig, times(1)).getApiKey();
        verify(summonerServiceConfig, times(1)).getBaseUri();
        verify(summonerServiceConfig, times(1)).getReadTimeout();
        verify(summonerServiceConfig, times(1)).getConnectTimeout();
        verify(restTemplateBuilder, times(1)).uriTemplateHandler(any());
    }
}
