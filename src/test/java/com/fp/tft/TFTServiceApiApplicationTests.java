package com.fp.tft;

import com.fp.tft.provider.summoner.SummonerProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("integration")
class TFTServiceApiApplicationTests {

	@Autowired
	private SummonerProvider summonerProvider;

	@Test
	void contextLoads() {
		summonerProvider.getSummonerByName("xdeathvdealerx");
	}

}
