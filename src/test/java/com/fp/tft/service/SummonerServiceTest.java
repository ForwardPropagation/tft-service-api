package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.mapper.SummonerMapper;
import com.fp.tft.provider.match.MatchProvider;
import com.fp.tft.provider.summoner.SummonerProvider;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SummonerServiceTest {

    @Mock
    private SummonerProvider summonerProvider;

    @Mock
    private MatchProvider matchProvider;

    @Mock
    private SummonerMapper summonerMapper;

    @InjectMocks
    private SummonerService objectToTest;

    @Test
    void testGetSummonerByName() {

        // Arrange
        var summonerName = "summonerName";
        var summonerDTO = new SummonerV4SummonerDTO();
        var expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerProvider.getSummonerByName(summonerName)).thenReturn(summonerDTO);
        when(summonerMapper.mapSummonerDtoToSummoner(summonerDTO)).thenReturn(expectedResponse);

        var inOrder = inOrder(summonerProvider, summonerMapper);

        // Act
        var res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider).getSummonerByName(summonerName);
        verify(summonerMapper).mapSummonerDtoToSummoner(summonerDTO);

        inOrder.verify(summonerProvider).getSummonerByName(summonerName);
        inOrder.verify(summonerMapper).mapSummonerDtoToSummoner(summonerDTO);
    }

    @Test
    void testGetSummonerByPuuid() {

        // Arrange
        var puuid = "summoner-123";
        var summonerDTO = new SummonerV4SummonerDTO();
        var expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerProvider.getSummonerByPuuid(puuid)).thenReturn(summonerDTO);
        when(summonerMapper.mapSummonerDtoToSummoner(summonerDTO)).thenReturn(expectedResponse);

        var inOrder = inOrder(summonerProvider, summonerMapper);

        // Act
        var res = objectToTest.getSummonerByPuuid(puuid);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider).getSummonerByPuuid(puuid);
        verify(summonerMapper).mapSummonerDtoToSummoner(summonerDTO);

        inOrder.verify(summonerProvider).getSummonerByPuuid(puuid);
        inOrder.verify(summonerMapper).mapSummonerDtoToSummoner(summonerDTO);
    }

    @Test
    void testGetSummonerMatchesByName() {

        // Arrange
        var summonerName = "summonerName";
        var count = 5;

        var summonerDTO = new SummonerV4SummonerDTO()
                .puuid("123553456345");
        List<String> matchIdList = Collections.emptyList();
        var expectedResponse = SummonerMatches.builder().build();

        when(summonerProvider.getSummonerByName(summonerName)).thenReturn(summonerDTO);
        when(matchProvider.getMatchIdListByPuuid(summonerDTO.getPuuid(), count)).thenReturn(matchIdList);
        when(summonerMapper.mapMatchListToSummonerMatches(matchIdList)).thenReturn(expectedResponse);

        var inOrder = inOrder(summonerProvider, matchProvider, summonerMapper);

        // Act
        var res = objectToTest.getSummonerMatchesByName(summonerName, count);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider).getSummonerByName(summonerName);
        verify(matchProvider).getMatchIdListByPuuid(summonerDTO.getPuuid(), count);
        verify(summonerMapper).mapMatchListToSummonerMatches(matchIdList);

        inOrder.verify(summonerProvider).getSummonerByName(summonerName);
        inOrder.verify(matchProvider).getMatchIdListByPuuid(summonerDTO.getPuuid(), count);
        inOrder.verify(summonerMapper).mapMatchListToSummonerMatches(matchIdList);
    }

    @Test
    void testGetSummonerMatchesByPuuid() {

        // Arrange
        var puuid = "summoner-123";
        var count = 5;

        List<String> matchIdList = Collections.emptyList();
        var expectedResponse = SummonerMatches.builder().build();

        when(matchProvider.getMatchIdListByPuuid(puuid, count)).thenReturn(matchIdList);
        when(summonerMapper.mapMatchListToSummonerMatches(matchIdList)).thenReturn(expectedResponse);

        var inOrder = inOrder(matchProvider, summonerMapper);

        // Act
        var res = objectToTest.getSummonerMatchesByPuuid(puuid, count);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider, never()).getSummonerByName(anyString());
        verify(matchProvider).getMatchIdListByPuuid(puuid, count);
        verify(summonerMapper).mapMatchListToSummonerMatches(matchIdList);

        inOrder.verify(matchProvider).getMatchIdListByPuuid(puuid, count);
        inOrder.verify(summonerMapper).mapMatchListToSummonerMatches(matchIdList);
    }
}
