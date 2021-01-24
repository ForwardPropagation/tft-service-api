package com.fp.tft.rest;

import com.fp.tft.api.SummonerApi;
import com.fp.tft.api.models.Summoner;
import com.fp.tft.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SummonerController implements SummonerApi {

    private final SummonerService summonerService;

    @Override
    public ResponseEntity<Summoner> getSummonerByName(String username) {
        return ResponseEntity.ok(summonerService.getSummonerByName(username));
    }
}
