package com.PI.Back.PIBackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PiBackendApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(PiBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PiBackendApplication.class, args);
		LOGGER.info("Running");
	}

}
