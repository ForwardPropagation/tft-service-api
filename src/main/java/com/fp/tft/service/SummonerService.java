package com.fp.tft.service;

import com.fp.tft.api.models.Summoner;
import com.fp.tft.provider.summoner.SummonerProvider;
import com.fp.tft.transformer.SummonerTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerProvider summonerProvider;

    private final SummonerTransformer summonerTransformer;

    public Summoner getSummonerByName(String summonerName) {
        return summonerTransformer.transformSummonerDtoToSummoner(summonerProvider.getSummonerByName(summonerName));
    }
}
