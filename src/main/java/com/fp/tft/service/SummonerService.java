package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.mapper.SummonerMapper;
import com.fp.tft.provider.match.MatchProvider;
import com.fp.tft.provider.summoner.SummonerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerProvider summonerProvider;

    private final MatchProvider matchProvider;

    private final SummonerMapper summonerMapper;

    public Summoner getSummonerByName(String summonerName) {
        return summonerMapper.mapSummonerDtoToSummoner(summonerProvider.getSummonerByName(summonerName));
    }

    public Summoner getSummonerByPuuid(String puuid) {
        return summonerMapper.mapSummonerDtoToSummoner(summonerProvider.getSummonerByPuuid(puuid));
    }

    public SummonerMatches getSummonerMatchesByName(String summonerName, Integer count) {
        return summonerMapper.mapMatchListToSummonerMatches(
                matchProvider.getMatchIdListByPuuid(summonerProvider.getSummonerByName(summonerName).getPuuid(), count));
    }

    public SummonerMatches getSummonerMatchesByPuuid(String puuid, Integer count) {
        return summonerMapper.mapMatchListToSummonerMatches(matchProvider.getMatchIdListByPuuid(puuid, count));
    }
}
