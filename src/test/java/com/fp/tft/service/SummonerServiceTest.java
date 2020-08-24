package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.provider.summoner.SummonerProvider;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import com.fp.tft.transformer.SummonerTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SummonerServiceTest {

    @Mock
    private SummonerProvider summonerProvider;

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
}
