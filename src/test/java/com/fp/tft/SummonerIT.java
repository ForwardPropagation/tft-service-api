package com.fp.tft;

import com.fp.tft.api.models.Summoner;
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
public class SummonerIT {

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
        final HttpHeaders httpHeaders = new HttpHeaders();

        String summonerName = "summonerTestName";
        stubGetBySummonerNameCall(summonerName);

        // Act
        final ResponseEntity<Summoner> response = testRestTemplate.exchange("/summoner/{summonerName}",
                HttpMethod.GET, new HttpEntity<>(httpHeaders), Summoner.class, summonerName);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Summoner res = response.getBody();
        assertEquals(summonerName, res.getSummonerName());
        assertEquals(31, res.getSummonerLevel());
    }

    private void stubGetBySummonerNameCall(String summonerName) {
        WireMock.stubFor(WireMock.get("/tft/summoner/v1/summoners/by-name/"+summonerName)
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("getBySummonerName_response_200.json")));
    }
}
