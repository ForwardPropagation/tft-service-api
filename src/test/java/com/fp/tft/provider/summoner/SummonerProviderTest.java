package com.fp.tft.provider.summoner;

import com.fp.tft.exception.ResourceNotFoundException;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @InjectMocks
    private SummonerProvider objectToTest;

    @Test
    void getSummonerByName() {

        // Arrange
        String summonerName = "testSummoner";
        SummonerV4SummonerDTO expectedRes = new SummonerV4SummonerDTO();

        when(restTemplate.getForEntity(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(SummonerV4SummonerDTO.class)))
                .thenReturn(ResponseEntity.ok(expectedRes));

        // Act
        SummonerV4SummonerDTO res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertEquals(expectedRes, res);

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByPuuid() {

        // Arrange
        String puuid = "summoner-123";
        SummonerV4SummonerDTO expectedRes = new SummonerV4SummonerDTO();

        when(restTemplate.getForEntity(eq("/"+SummonerProvider.BY_PUUID_PATH+"/"+puuid), eq(SummonerV4SummonerDTO.class)))
                .thenReturn(ResponseEntity.ok(expectedRes));

        // Act
        SummonerV4SummonerDTO res = objectToTest.getSummonerByPuuid(puuid);

        // Assert
        assertEquals(expectedRes, res);

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByName_Downstream_Exception() {

        // Arrange
        String summonerName = "testSummoner";

        RestClientResponseException exception = mock(RestClientResponseException.class);
        when(exception.getRawStatusCode()).thenReturn(500);
        when(exception.getResponseBodyAsString()).thenReturn("");

        when(restTemplate.getForEntity(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(SummonerV4SummonerDTO.class)))
                .thenThrow(exception);

        // Act & Assert
        assertThrows(SummonerServiceException.class, () -> objectToTest.getSummonerByName(summonerName));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByName_Downstream_Not_Found_Exception() {

        // Arrange
        String summonerName = "testSummoner";

        RestClientResponseException exception = mock(RestClientResponseException.class);
        when(exception.getRawStatusCode()).thenReturn(404);
        when(exception.getResponseBodyAsString()).thenReturn("");

        when(restTemplate.getForEntity(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(SummonerV4SummonerDTO.class)))
                .thenThrow(exception);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> objectToTest.getSummonerByName(summonerName));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(SummonerV4SummonerDTO.class));
    }

    @Test
    void getSummonerByName_Exception() {

        // Arrange
        String summonerName = "testSummoner";

        when(restTemplate.getForEntity(eq("/"+SummonerProvider.BY_NAME_PATH+"/"+summonerName), eq(SummonerV4SummonerDTO.class)))
                .thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(SummonerServiceException.class, () -> objectToTest.getSummonerByName(summonerName));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(SummonerV4SummonerDTO.class));
    }
}
