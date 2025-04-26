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
@EnableConfigurationProperties({AnthropicProperties.class, KnlProperties.class})
@Configuration
public class WebClientConfig {

	private final AnthropicProperties anthropicProperties;

	private String anthropicApiKey;
	private String anthropicBaseUrl;
	private String anthropicApiVersion;
	private int timeout;

	private final KnlProperties knlProperties;

	private String knlBaseUrl;

	@PostConstruct
	public void init() {
		this.anthropicApiKey = anthropicProperties.getKey();
		this.anthropicBaseUrl = anthropicProperties.getBaseUrl();
		this.anthropicApiVersion = anthropicProperties.getVersion();
		this.timeout = anthropicProperties.getTimeout();
		this.knlBaseUrl = knlProperties.getBaseUrl();
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

	// @Qualifier("anthropicWebClient")
	@Bean
	public WebClient anthropicWebClient() {

		HttpClient httpClient = HttpClient.create(ConnectionProvider.builder("anthropic-connection-pool")
				.maxConnections(50)
				.maxIdleTime(Duration.ofSeconds(60))
				.build())
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(timeout));

		return WebClient.builder()
			.baseUrl(anthropicBaseUrl)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.codecs(configure -> configure.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader("x-api-key", anthropicApiKey)
			.defaultHeader("anthropic-version", anthropicApiVersion)
			.build();
	}

	// @Qualifier("knlWebClient")
	@Bean
	public WebClient knlWebClient() {

		HttpClient httpClient = HttpClient.create(ConnectionProvider.builder("knl-connection-pool")
				.maxConnections(50)
				.maxIdleTime(Duration.ofSeconds(60))
				.build())
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(timeout));

		return WebClient.builder()
			.baseUrl(knlBaseUrl)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
}
