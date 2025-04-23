package com.oops.reasonaible.core.config;

import java.time.Duration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@RequiredArgsConstructor
@EnableConfigurationProperties(AnthropicProperties.class)
@Configuration
public class AnthropicConfig {

	private final AnthropicProperties anthropicProperties;

	private String apiKey;
	private String baseUrl;
	private String apiVersion;
	private int timeout;

	@PostConstruct
	public void init() {
		this.apiKey = anthropicProperties.getKey();
		this.baseUrl = anthropicProperties.getBaseUrl();
		this.apiVersion = anthropicProperties.getVersion();
		this.timeout = anthropicProperties.getTimeout();
	}
	// @Value("${anthropic.key}")
	// private String apiKey;
	//
	// @Value("${anthropic.base-url}")
	// private String baseUrl;
	//
	// @Value("${anthropic.version}")
	// private String apiVersion;
	//
	// @Value("${anthropic.timeout}")
	// private int timeout;

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
