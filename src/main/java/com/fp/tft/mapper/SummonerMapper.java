package com.fp.tft.mapper;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SummonerMapper {

    @Mapping(target = "summonerId", source = "id")
    @Mapping(target = "summonerName", source = "name")
    Summoner mapSummonerDtoToSummoner(SummonerV4SummonerDTO summonerDTO);

    default SummonerMatches mapMatchListToSummonerMatches(List<String> matchIdList) {
        return mapMatchListToSummonerMatches(matchIdList.size(), matchIdList);
    }

    SummonerMatches mapMatchListToSummonerMatches(Integer matchCount, List<String> matchIds);
}
