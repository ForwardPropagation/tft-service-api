package com.fp.tft.rest;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
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

    @Test
    void testGetSummonerByPuuid() {

        // Arrange
        String puuid = "summoner-123";
        Summoner expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerService.getSummonerByPuuid(puuid)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Summoner> res = objectToTest.getSummonerByPuuid(puuid);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerByPuuid(puuid);
    }

    @Test
    void testGetMatchesBySummonerName() {

        // Arrange
        String summonerName = "testUser";
        Integer count = 10;
        SummonerMatches expectedResponse = SummonerMatches.builder().build();

        when(summonerService.getSummonerMatchesByName(summonerName, count)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<SummonerMatches> res = objectToTest.getMatchesBySummonerName(summonerName, count);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerMatchesByName(summonerName, count);
    }

    @Test
    void testGetMatchesBySummonerPuuid() {

        // Arrange
        String puuid = "summoner-123";
        Integer count = 10;
        SummonerMatches expectedResponse = SummonerMatches.builder().build();

        when(summonerService.getSummonerMatchesByPuuid(puuid, count)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<SummonerMatches> res = objectToTest.getMatchesBySummonerPuuid(puuid, count);

        // Assert
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(expectedResponse, res.getBody());

        verify(summonerService, times(1)).getSummonerMatchesByPuuid(puuid, count);
    }
}
