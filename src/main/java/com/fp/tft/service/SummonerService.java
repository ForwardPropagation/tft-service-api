package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.provider.match.MatchProvider;
import com.fp.tft.provider.summoner.SummonerProvider;
import com.fp.tft.transformer.SummonerTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerProvider summonerProvider;

    private final MatchProvider matchProvider;

    private final SummonerTransformer summonerTransformer;

    public Summoner getSummonerByName(String summonerName) {
        return summonerTransformer.transformSummonerDtoToSummoner(summonerProvider.getSummonerByName(summonerName));
    }

    public Summoner getSummonerByPuuid(String puuid) {
        return summonerTransformer.transformSummonerDtoToSummoner(summonerProvider.getSummonerByPuuid(puuid));
    }

    public SummonerMatches getSummonerMatchesByName(String summonerName, Integer count) {
        return summonerTransformer.transformMatchListToSummonerMatches(
                matchProvider.getMatchIdListByPuuid(summonerProvider.getSummonerByName(summonerName).getPuuid(), count));
    }

    public SummonerMatches getSummonerMatchesByPuuid(String puuid, Integer count) {
        return summonerTransformer.transformMatchListToSummonerMatches(matchProvider.getMatchIdListByPuuid(puuid, count));
    }
}
