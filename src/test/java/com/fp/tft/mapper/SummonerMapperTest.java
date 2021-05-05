package com.fp.tft.mapper;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SummonerMapperTest {

    private final SummonerMapper objectToTest = Mappers.getMapper(SummonerMapper.class);

    @Test
    void testMapSummonerDtoToSummoner() {

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
        Summoner res = objectToTest.mapSummonerDtoToSummoner(summonerDTO);

        // Assert
        assertNotNull(res);
        assertEquals(summonerDTO.getId(), res.getSummonerId());
        assertEquals(summonerDTO.getPuuid(), res.getPuuid());
        assertEquals(summonerDTO.getName(), res.getSummonerName());
        assertEquals(summonerDTO.getSummonerLevel(), res.getSummonerLevel());
        assertEquals(summonerDTO.getRevisionDate(), res.getRevisionDate());
    }

    @Test
    void testMapSummonerDtoToSummoner_Null() {

        // Arrange & Act
        Summoner res = objectToTest.mapSummonerDtoToSummoner(null);

        // Assert
        assertNull(res);
    }

    @Test
    void testMapMatchListToSummonerMatches() {

        // Arrange
        List<String> matchIdList = Arrays.asList("NA_1", "NA_2", "NA_3");

        // Act
        SummonerMatches res = objectToTest.mapMatchListToSummonerMatches(matchIdList);

        // Assert
        assertNotNull(res);
        assertEquals(matchIdList.size(), res.getMatchCount());
        assertEquals(matchIdList, res.getMatchIds());
    }

    @Test
    void testMapMatchListToSummonerMatches_Null() {

        // Arrange & Act
        SummonerMatches res = objectToTest.mapMatchListToSummonerMatches(null);

        // Assert
        assertNull(res);
    }

    @Test
    void testMapMatchListToSummonerMatches_Null_MatchCount() {

        // Arrange
        List<String> matchIdList = Arrays.asList("NA_1", "NA_2", "NA_3");

        // Act
        SummonerMatches res = objectToTest.mapMatchListToSummonerMatches(null, matchIdList);

        // Assert
        assertNotNull(res);
        assertNull(res.getMatchCount());
        assertEquals(matchIdList, res.getMatchIds());
    }

    @Test
    void testMapMatchListToSummonerMatches_Null_MatchIds() {

        // Arrange
        List<String> matchIdList = Arrays.asList("NA_1", "NA_2", "NA_3");

        // Act
        SummonerMatches res = objectToTest.mapMatchListToSummonerMatches(matchIdList.size(), null);

        // Assert
        assertNotNull(res);
        assertEquals(matchIdList.size(), res.getMatchCount());
        assertNull(res.getMatchIds());
    }
}
