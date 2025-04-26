package com.oops.reasonaible.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @RequiredArgsConstructor
@ConfigurationProperties(prefix = "knl")
@Configuration
public class KnlProperties {

	// private String key;
	private String baseUrl;
	private String model;
	private int maxTokens;
	private double temperature;
}
