package com.fp.tft.provider.summoner;

import com.fp.tft.provider.TFTServiceConfig;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SummonerProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TFTServiceConfig tftServiceConfig;

    @InjectMocks
    private SummonerProvider objectToTest;

    private final static String APIKEY = "testKey";

    @Test
    void getSummonerByName() {

        // Arrange
        String summonerName = "testSummoner";
        SummonerV4SummonerDTO expectedRes = new SummonerV4SummonerDTO();

        when(tftServiceConfig.getApiKey()).thenReturn(APIKEY);
        when(restTemplate.exchange(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(SummonerV4SummonerDTO.class))).thenReturn(ResponseEntity.ok(expectedRes));

        // Act
        SummonerV4SummonerDTO res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertEquals(expectedRes, res);

        verify(tftServiceConfig, times(1)).getApiKey();
        verify(restTemplate, times(1))
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByName_Downstream_Exception() {

        // Arrange
        String summonerName = "testSummoner";

        when(tftServiceConfig.getApiKey()).thenReturn(APIKEY);

        RestClientResponseException exception = mock(RestClientResponseException.class);
        when(exception.getRawStatusCode()).thenReturn(500);
        when(exception.getResponseBodyAsString()).thenReturn("");

        when(restTemplate.exchange(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(SummonerV4SummonerDTO.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(SummonerServiceException.class, () -> objectToTest.getSummonerByName(summonerName));

        verify(tftServiceConfig, times(1)).getApiKey();
        verify(restTemplate, times(1))
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByName_Exception() {

        // Arrange
        String summonerName = "testSummoner";
        when(tftServiceConfig.getApiKey()).thenThrow(new RuntimeException("Error"));

        // Act & Assert
        assertThrows(SummonerServiceException.class, () -> objectToTest.getSummonerByName(summonerName));

        verify(tftServiceConfig, times(1)).getApiKey();
        verify(restTemplate, times(0))
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(SummonerV4SummonerDTO.class));
    }
}
