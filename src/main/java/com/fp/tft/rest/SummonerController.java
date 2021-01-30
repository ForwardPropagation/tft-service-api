package com.fp.tft.rest;

import com.fp.tft.api.SummonerApi;
import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SummonerController implements SummonerApi {

    private final SummonerService summonerService;

    @Override
    public ResponseEntity<Summoner> getSummonerByName(String username) {
        return ResponseEntity.ok(summonerService.getSummonerByName(username));
    }

    @Override
    public ResponseEntity<SummonerMatches> getMatchesBySummonerName(String summonerName, @Valid Integer count) {
        return ResponseEntity.ok(summonerService.getSummonerMatchesByName(summonerName, count));
    }
}
