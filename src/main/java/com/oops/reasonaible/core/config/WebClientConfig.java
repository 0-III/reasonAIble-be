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
import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@RequiredArgsConstructor
@EnableConfigurationProperties(KnlProperties.class)
@Configuration
public class WebClientConfig {

	private final KnlProperties knlProperties;

	@Bean
	public WebClient knlWebClient() {

		HttpClient httpClient = HttpClient.create(ConnectionProvider.builder("knl-connection-pool")
				.maxConnections(50)
				.maxIdleTime(Duration.ofSeconds(60))
				.build())
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);
		// .responseTimeout(Duration.ofSeconds(timeout));

		return WebClient.builder()
			.baseUrl(knlProperties.getBaseUrl())
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
}
