package com.oops.reasonaible.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class AnthropicConfig {

	@Value("${anthropic.key}")
	private String apiKey;

	@Value("${anthropic.base-url}")
	private String baseUrl;

	@Value("${anthropic.version}")
	private String apiVersion;

	@Value("${anthropic.timeout}")
	private int timeout;

	// @Value("${anthropic.model}")
	// private String model;

	@Bean
	public WebClient anthropicWebClient() {

		HttpClient httpClient = HttpClient.create(ConnectionProvider.builder("anthropic-connection-pool")
				.maxConnections(50)
				.maxIdleTime(Duration.ofSeconds(60))
				.build())
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(timeout));

		return WebClient.builder()
			.baseUrl(baseUrl)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.codecs(configure -> configure.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader("x-api-key", apiKey)
			.defaultHeader("anthropic-version", apiVersion)
			.build();
	}
}
