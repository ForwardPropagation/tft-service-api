package com.fp.tft.rest;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.rest.SummonerController;
import com.fp.tft.service.SummonerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SummonerControllerTest {

    @Mock
    private SummonerService summonerService;

    @InjectMocks
    private SummonerController objectToTest;

    @Test
    void testGetSummonerByName() {

        // Arrange
        String summonerName = "testUser";
        Summoner expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerService.getSummonerByName(summonerName)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Summoner> res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerByName(summonerName);
    }
}
