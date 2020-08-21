package com.fp.tft.transformer;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.riot.api.SummonerV4SummonerDTO;
import org.springframework.stereotype.Component;

@Component
public class SummonerTransformer {

    public Summoner transformSummonerDtoToSummoner(SummonerV4SummonerDTO summonerDTO) {
        Summoner summoner = new Summoner();
        summoner.setSummonerName(summonerDTO.getName());
        summoner.setSummonerId(summonerDTO.getId());
        summoner.setPuuid(summonerDTO.getPuuid());
        summoner.setSummonerLevel(summonerDTO.getSummonerLevel());
        summoner.setRevisionDate(summonerDTO.getRevisionDate());
        return summoner;
    }
}
