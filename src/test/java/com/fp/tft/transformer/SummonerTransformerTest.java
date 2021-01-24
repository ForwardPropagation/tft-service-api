package com.fp.tft.transformer;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SummonerTransformerTest {

    @InjectMocks
    private SummonerTransformer objectToTest;

    @Test
    void testTransformSummonerDtoToSummoner() {

        // Arrange
        SummonerV4SummonerDTO summonerDTO = new SummonerV4SummonerDTO();
        summonerDTO.setId("summoner-123");
        summonerDTO.setAccountId("account-321");
        summonerDTO.setName("summonerName");
        summonerDTO.setProfileIconId(23);
        summonerDTO.setPuuid("1234-1234-3211");
        summonerDTO.setSummonerLevel(32L);
        summonerDTO.setRevisionDate(15645654465L);

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
}
