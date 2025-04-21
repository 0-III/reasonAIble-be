package com.oops.reasonaible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ReasonAIbleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReasonAIbleApplication.class, args);
	}

}
