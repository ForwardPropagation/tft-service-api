package com.fp.tft.provider.match;

import com.fp.tft.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MatchProvider objectToTest;

    @Test
    void getMatchIdListByPuuid() {

        // Arrange
        var puuid = "lLsFGUayoAmkT4Vug6X9jqt8_gF05Iuhc8rsYlu1QLHRZyExXvJqybBXIUWzeZXm8TJPVMM7w1BdQQ";
        var count = 100;
        var expectedRes = new ArrayList<String>();

        when(restTemplate.exchange(eq("/"+MatchProvider.BY_PUUID_PATH+"/"+puuid+"/"+MatchProvider.ID_PATH+"?"+MatchProvider.COUNT_QUERY_PARAM+"="+count),
                eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(expectedRes));

        // Act
        var res = objectToTest.getMatchIdListByPuuid(puuid, count);

        // Assert
        assertEquals(expectedRes, res);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));
    }

    @Test
    void getMatchIdListByPuuid_Null_Count() {

        // Arrange
        var puuid = "lLsFGUayoAmkT4Vug6X9jqt8_gF05Iuhc8rsYlu1QLHRZyExXvJqybBXIUWzeZXm8TJPVMM7w1BdQQ";
        var expectedRes = new ArrayList<String>();

        when(restTemplate.exchange(eq("/"+MatchProvider.BY_PUUID_PATH+"/"+puuid+"/"+MatchProvider.ID_PATH+"?"+MatchProvider.COUNT_QUERY_PARAM+"=10"),
                eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(expectedRes));

        // Act
        var res = objectToTest.getMatchIdListByPuuid(puuid, null);

        // Assert
        assertEquals(expectedRes, res);

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));
    }

    @Test
    void getMatchIdListByPuuid_Downstream_Exception() {

        // Arrange
        var puuid = "lLsFGUayoAmkT4Vug6X9jqt8_gF05Iuhc8rsYlu1QLHRZyExXvJqybBXIUWzeZXm8TJPVMM7w1BdQQ";
        var count = 100;

        var exception = mock(RestClientResponseException.class);
        when(exception.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(exception.getResponseBodyAsString()).thenReturn("");

        when(restTemplate.exchange(eq("/"+MatchProvider.BY_PUUID_PATH+"/"+puuid+"/"+MatchProvider.ID_PATH+"?"+MatchProvider.COUNT_QUERY_PARAM+"="+count),
                eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(MatchServiceException.class, () -> objectToTest.getMatchIdListByPuuid(puuid, count));

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));
    }

    @Test
    void getMatchIdListByPuuid_Downstream_Not_Found_Exception() {

        // Arrange
        var puuid = "lLsFGUayoAmkT4Vug6X9jqt8_gF05Iuhc8rsYlu1QLHRZyExXvJqybBXIUWzeZXm8TJPVMM7w1BdQQ";
        var count = 100;

        var exception = mock(RestClientResponseException.class);
        when(exception.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(exception.getResponseBodyAsString()).thenReturn("");

        when(restTemplate.exchange(eq("/"+MatchProvider.BY_PUUID_PATH+"/"+puuid+"/"+MatchProvider.ID_PATH+"?"+MatchProvider.COUNT_QUERY_PARAM+"="+count),
                eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class))).thenThrow(exception);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> objectToTest.getMatchIdListByPuuid(puuid, count));

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));
    }

    @Test
    void getMatchIdListByPuuid_Exception() {

        // Arrange
        var puuid = "lLsFGUayoAmkT4Vug6X9jqt8_gF05Iuhc8rsYlu1QLHRZyExXvJqybBXIUWzeZXm8TJPVMM7w1BdQQ";
        var count = 100;

        when(restTemplate.exchange(eq("/"+MatchProvider.BY_PUUID_PATH+"/"+puuid+"/"+MatchProvider.ID_PATH+"?"+MatchProvider.COUNT_QUERY_PARAM+"="+count),
                eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class))).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(MatchServiceException.class, () -> objectToTest.getMatchIdListByPuuid(puuid, count));

        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class));
    }
}
