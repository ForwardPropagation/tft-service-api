package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.provider.match.MatchProvider;
import com.fp.tft.provider.summoner.SummonerProvider;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import com.fp.tft.transformer.SummonerTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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
    private SummonerTransformer summonerTransformer;

    @InjectMocks
    private SummonerService objectToTest;

    @Test
    void testGetSummonerByName() {

        // Arrange
        String summonerName = "summonerName";
        SummonerV4SummonerDTO summonerDTO = new SummonerV4SummonerDTO();
        Summoner expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerProvider.getSummonerByName(summonerName)).thenReturn(summonerDTO);
        when(summonerTransformer.transformSummonerDtoToSummoner(summonerDTO)).thenReturn(expectedResponse);

        InOrder inOrder = inOrder(summonerProvider, summonerTransformer);

        // Act
        Summoner res = objectToTest.getSummonerByName(summonerName);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider, times(1)).getSummonerByName(summonerName);
        verify(summonerTransformer, times(1)).transformSummonerDtoToSummoner(summonerDTO);

        inOrder.verify(summonerProvider).getSummonerByName(summonerName);
        inOrder.verify(summonerTransformer).transformSummonerDtoToSummoner(summonerDTO);
    }

    @Test
    void testGetSummonerByPuuid() {

        // Arrange
        String puuid = "summoner-123";
        SummonerV4SummonerDTO summonerDTO = new SummonerV4SummonerDTO();
        Summoner expectedResponse = Summoner.builder().summonerId("summoner-123").build();

        when(summonerProvider.getSummonerByPuuid(puuid)).thenReturn(summonerDTO);
        when(summonerTransformer.transformSummonerDtoToSummoner(summonerDTO)).thenReturn(expectedResponse);

        InOrder inOrder = inOrder(summonerProvider, summonerTransformer);

        // Act
        Summoner res = objectToTest.getSummonerByPuuid(puuid);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider, times(1)).getSummonerByPuuid(puuid);
        verify(summonerTransformer, times(1)).transformSummonerDtoToSummoner(summonerDTO);

        inOrder.verify(summonerProvider).getSummonerByPuuid(puuid);
        inOrder.verify(summonerTransformer).transformSummonerDtoToSummoner(summonerDTO);
    }

    @Test
    void testGetSummonerMatchesByName() {

        // Arrange
        String summonerName = "summonerName";
        Integer count = 5;

        SummonerV4SummonerDTO summonerDTO = new SummonerV4SummonerDTO()
                .puuid("123553456345");
        List<String> matchIdList = Collections.emptyList();
        SummonerMatches expectedResponse = SummonerMatches.builder().build();

        when(summonerProvider.getSummonerByName(summonerName)).thenReturn(summonerDTO);
        when(matchProvider.getMatchIdListByPuuid(summonerDTO.getPuuid(), count)).thenReturn(matchIdList);
        when(summonerTransformer.transformMatchListToSummonerMatches(summonerDTO, matchIdList)).thenReturn(expectedResponse);

        InOrder inOrder = inOrder(summonerProvider, matchProvider, summonerTransformer);

        // Act
        SummonerMatches res = objectToTest.getSummonerMatchesByName(summonerName, count);

        // Assert
        assertEquals(expectedResponse, res);

        verify(summonerProvider, times(1)).getSummonerByName(summonerName);
        verify(matchProvider, times(1)).getMatchIdListByPuuid(summonerDTO.getPuuid(), count);
        verify(summonerTransformer, times(1)).transformMatchListToSummonerMatches(summonerDTO, matchIdList);

        inOrder.verify(summonerProvider).getSummonerByName(summonerName);
        inOrder.verify(matchProvider).getMatchIdListByPuuid(summonerDTO.getPuuid(), count);
        inOrder.verify(summonerTransformer).transformMatchListToSummonerMatches(summonerDTO, matchIdList);
    }
}
