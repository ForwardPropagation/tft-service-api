package com.fp.tft.transformer;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SummonerTransformerTest {

    @InjectMocks
    private SummonerTransformer objectToTest;

    @Test
    void testTransformSummonerDtoToSummoner() {

        // Arrange
        SummonerV4SummonerDTO summonerDTO = new SummonerV4SummonerDTO()
                .id("summoner-123")
                .accountId("account-321")
                .name("summonerName")
                .profileIconId(23)
                .puuid("1234-1234-3211")
                .summonerLevel(32L)
                .revisionDate(15645654465L);

        // Act
        Summoner res = objectToTest.transformSummonerDtoToSummoner(summonerDTO);

        // Assert
        assertNotNull(res);
        assertEquals(summonerDTO.getId(), res.getSummonerId());
        assertEquals(summonerDTO.getPuuid(), res.getPuuid());
        assertEquals(summonerDTO.getName(), res.getSummonerName());
        assertEquals(summonerDTO.getSummonerLevel(), res.getSummonerLevel());
        assertEquals(summonerDTO.getRevisionDate(), res.getRevisionDate());
    }

    @Test
    void testTransformMatchListToSummonerMatches() {

        // Arrange
        List<String> matchIdList = Arrays.asList("NA_1", "NA_2", "NA_3");

        // Act
        SummonerMatches res = objectToTest.transformMatchListToSummonerMatches(matchIdList);

        // Assert
        assertNotNull(res);
        assertEquals(matchIdList.size(), res.getMatchCount());
        assertEquals(matchIdList, res.getMatchIds());
    }
}
