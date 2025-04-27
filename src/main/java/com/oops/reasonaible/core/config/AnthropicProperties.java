package com.oops.reasonaible.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "anthropic")
@Configuration
public class AnthropicProperties {
	// private String key;
	// private String baseUrl;
	// private String version;
	// private int timeout;
	private final String model;
	private final int maxTokens;
	private final double temperature;
}
