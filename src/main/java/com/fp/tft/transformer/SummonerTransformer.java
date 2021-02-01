package com.fp.tft.transformer;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SummonerTransformer {

    public Summoner transformSummonerDtoToSummoner(SummonerV4SummonerDTO summonerDTO) {
        return Summoner.builder()
                .summonerName(summonerDTO.getName())
                .summonerId(summonerDTO.getId())
                .puuid(summonerDTO.getPuuid())
                .summonerLevel(summonerDTO.getSummonerLevel())
                .revisionDate(summonerDTO.getRevisionDate())
                .build();
    }

    public SummonerMatches transformMatchListToSummonerMatches(List<String> matchIdList) {
        return SummonerMatches.builder()
                .matchCount(matchIdList.size())
                .matchIds(matchIdList)
                .build();
    }
}
