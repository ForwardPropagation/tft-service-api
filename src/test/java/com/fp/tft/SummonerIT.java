package com.fp.tft;

import com.fp.tft.api.models.ServerError;
import com.fp.tft.api.models.Summoner;
import com.fp.tft.api.models.SummonerMatches;
import com.fp.tft.exception.ErrorCodes;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, WiremockTestExtension.class})
@ActiveProfiles("integration")
class SummonerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void init() {
        WireMock.removeAllMappings();
    }

    @DisplayName("Test getSummonerByName OK")
    @Test
    void getSummonerByName() {

        // Arrange
        final var httpHeaders = new HttpHeaders();

        var summonerName = "summonerTestName";
        stubGetBySummonerNameCall(summonerName);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-name/{summonerName}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), Summoner.class, summonerName);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(summonerName, res.getSummonerName());
        assertEquals(31, res.getSummonerLevel());
    }

    @DisplayName("Test getSummonerByPuuid OK")
    @Test
    void getSummonerByPuuid() {

        // Arrange
        final var httpHeaders = new HttpHeaders();

        var puuid = "rN6W0P66QrW1LEl2Ayykh4FFLeUhKaevDn8ew4VbjMfA6Bs_aZTT9xOkseJct7C3otRUgmWWJTbF3Q";
        stubGetBySummonerPuuidCall(puuid);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-id/{puuid}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), Summoner.class, puuid);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(puuid, res.getPuuid());
        assertEquals(31, res.getSummonerLevel());
    }

    @DisplayName("Test getSummonerByName Downstream Error")
    @Test
    void getSummonerByName_Downstream_Error() {

        // Arrange
        final var httpHeaders = new HttpHeaders();

        var summonerName = "summonerTestName";
        stubGetBySummonerNameCall_Server_Not_Found_Error(summonerName);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-name/{summonerName}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), ServerError.class, summonerName);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), res.getCode());
        assertEquals(ErrorCodes.NOT_FOUND.getResponseErrorCode(), res.getMessage());
    }

    @DisplayName("Test getSummonerByName Downstream Not Found Error")
    @Test
    void getSummonerByName_Downstream_Not_Found_Error() {

        // Arrange
        final var httpHeaders = new HttpHeaders();

        var summonerName = "summonerTestName";
        stubGetBySummonerNameCall_Server_Error(summonerName);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-name/{summonerName}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), ServerError.class, summonerName);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), res.getCode());
        assertEquals(ErrorCodes.TFT_SERVICE_ERROR.getResponseErrorCode(), res.getMessage());
    }

    @DisplayName("Test getMatchesBySummonerName OK")
    @Test
    void getMatchesBySummonerName() {
        // Arrange
        final var httpHeaders = new HttpHeaders();

        var summonerName = "summonerTestName";
        var puuid = "rN6W0P66QrW1LEl2Ayykh4FFLeUhKaevDn8ew4VbjMfA6Bs_aZTT9xOkseJct7C3otRUgmWWJTbF3Q";
        var count = 5;
        stubGetBySummonerNameCall(summonerName);
        stubGetMatchIdListByPuuid(puuid, count);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-name/{summonerName}/matches?count={count}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), SummonerMatches.class, summonerName, count);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(4, res.getMatchCount());
        assertNotNull(res.getMatchIds());
        assertEquals(4, res.getMatchIds().size());
    }

    @DisplayName("Test getMatchesBySummonerPuuid OK")
    @Test
    void getMatchesBySummonerPuuid() {
        // Arrange
        final var httpHeaders = new HttpHeaders();

        var puuid = "rN6W0P66QrW1LEl2Ayykh4FFLeUhKaevDn8ew4VbjMfA6Bs_aZTT9xOkseJct7C3otRUgmWWJTbF3Q";
        var count = 5;
        stubGetMatchIdListByPuuid(puuid, count);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-id/{puuid}/matches?count={count}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), SummonerMatches.class, puuid, count);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(4, res.getMatchCount());
        assertNotNull(res.getMatchIds());
        assertEquals(4, res.getMatchIds().size());
    }

    @DisplayName("Test getMatchesBySummonerName OK")
    @Test
    void getMatchesBySummonerName_Downstream_Error() {
        // Arrange
        final var httpHeaders = new HttpHeaders();

        var summonerName = "summonerTestName";
        var puuid = "rN6W0P66QrW1LEl2Ayykh4FFLeUhKaevDn8ew4VbjMfA6Bs_aZTT9xOkseJct7C3otRUgmWWJTbF3Q";
        var count = 5;
        stubGetBySummonerNameCall(summonerName);
        stubGetMatchIdListByPuuid_Server_Error(puuid, count);

        // Act
        final var response = testRestTemplate.exchange("/summoner/by-name/{summonerName}/matches?count={count}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), ServerError.class, summonerName, count);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        var res = response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), res.getCode());
        assertEquals(ErrorCodes.TFT_SERVICE_ERROR.getResponseErrorCode(), res.getMessage());
    }

    private void stubGetBySummonerNameCall(String summonerName) {
        WireMock.stubFor(WireMock.get("/tft/summoner/v1/summoners/by-name/"+summonerName)
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("getBySummoner_response_200.json")));
    }

    private void stubGetBySummonerPuuidCall(String puuid) {
        WireMock.stubFor(WireMock.get("/tft/summoner/v1/summoners/by-puuid/"+puuid)
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("getBySummoner_response_200.json")));
    }

    private void stubGetBySummonerNameCall_Server_Error(String summonerName) {
        WireMock.stubFor(WireMock.get("/tft/summoner/v1/summoners/by-name/"+summonerName)
                .willReturn(WireMock.aResponse().withStatus(500)));
    }

    private void stubGetBySummonerNameCall_Server_Not_Found_Error(String summonerName) {
        WireMock.stubFor(WireMock.get("/tft/summoner/v1/summoners/by-name/"+summonerName)
                .willReturn(WireMock.aResponse().withStatus(404)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("getBySummoner_response_404.json")));
    }

    private void stubGetMatchIdListByPuuid(String puuid, Integer count) {
        WireMock.stubFor(WireMock.get("/tft/match/v1/matches/by-puuid/"+puuid+"/ids?count="+count)
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("getMatchIdListByPuuid_response_200.json")));
    }

    private void stubGetMatchIdListByPuuid_Server_Error(String puuid, Integer count) {
        WireMock.stubFor(WireMock.get("/tft/match/v1/matches/by-puuid/"+puuid+"/ids?count="+count)
                .willReturn(WireMock.aResponse().withStatus(500)));
    }
}
