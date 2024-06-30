package com.fp.tft.provider.match;

import com.fp.tft.provider.TFTServiceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTemplateProviderTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private TFTServiceConfig tftServiceConfig;

    @Mock
    private MatchServiceConfig matchServiceConfig;

    @InjectMocks
    private MatchServiceTemplateProvider objectToTest;

    @Test
    void getSummonerServiceRestTemplate() {

        // Arrange
        when(tftServiceConfig.getApiKey()).thenReturn("key");
        when(matchServiceConfig.getBaseUri()).thenReturn("");
        when(matchServiceConfig.getReadTimeout()).thenReturn(1000);
        when(matchServiceConfig.getConnectTimeout()).thenReturn(1000);
        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(new RestTemplateBuilder());

        // Act
        var res = objectToTest.getMatchServiceRestTemplate();

        // Assert
        assertNotNull(res);

        verify(tftServiceConfig).getApiKey();
        verify(matchServiceConfig).getBaseUri();
        verify(matchServiceConfig).getReadTimeout();
        verify(matchServiceConfig).getConnectTimeout();
        verify(restTemplateBuilder).uriTemplateHandler(any());
    }
}
