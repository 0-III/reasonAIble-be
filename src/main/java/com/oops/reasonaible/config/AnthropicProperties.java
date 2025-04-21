package com.oops.reasonaible.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @RequiredArgsConstructor
@ConfigurationProperties(prefix = "anthropic")
@Configuration
public class AnthropicProperties {
	// @Value("${anthropic.key}")
	private String key;

	// @Value("${anthropic.base-url}")
	private String baseUrl;

	// @Value("${anthropic.version}")
	private String version;

	// @Value("${anthropic.timeout}")
	private int timeout;

	private String model;
	private int maxTokens;
	private double temperature;
}
