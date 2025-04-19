package com.oops.reasonaible.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AnthropicConfig {
	private WebClient webClient;

	public AnthropicConfig(
		@Value("${anthropic-api.base-url}") String baseUrl,
		@Value("${anthropic-api.api-key}") String apiKey
	) {
		this.webClient = WebClient.builder()
			.baseUrl(baseUrl)
			.defaultHeader("x-api-key", apiKey)
			.defaultHeader("anthropic-version", "2023-06-01")
			.defaultHeader("Content-Type", "application/json")
			.build();
	}
}
