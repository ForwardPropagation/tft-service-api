package com.fp.tft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fp.tft"})
public class TFTServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TFTServiceApiApplication.class, args);
	}

}
