package com.fp.tft.controller;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.service.SummonerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

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
        var summonerName = "testUser";
        var expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerService.getSummonerByName(summonerName)).thenReturn(expectedResponse);

        // Act
        var res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerByName(summonerName);
    }

    @Test
    void testGetSummonerByPuuid() {

        // Arrange
        var puuid = "summoner-123";
        var expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerService.getSummonerByPuuid(puuid)).thenReturn(expectedResponse);

        // Act
        var res = objectToTest.getSummonerByPuuid(puuid);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerByPuuid(puuid);
    }

    @Test
    void testGetMatchesBySummonerName() {

        // Arrange
        var summonerName = "testUser";
        var count = 10;
        var expectedResponse = SummonerMatches.builder().build();

        when(summonerService.getSummonerMatchesByName(summonerName, count)).thenReturn(expectedResponse);

        // Act
        var res = objectToTest.getMatchesBySummonerName(summonerName, count);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerMatchesByName(summonerName, count);
    }

    @Test
    void testGetMatchesBySummonerPuuid() {

        // Arrange
        var puuid = "summoner-123";
        var count = 10;
        var expectedResponse = SummonerMatches.builder().build();

        when(summonerService.getSummonerMatchesByPuuid(puuid, count)).thenReturn(expectedResponse);

        // Act
        var res = objectToTest.getMatchesBySummonerPuuid(puuid, count);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerMatchesByPuuid(puuid, count);
    }
}
