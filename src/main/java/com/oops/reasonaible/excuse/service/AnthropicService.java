package com.oops.reasonaible.excuse.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oops.reasonaible.core.config.AnthropicProperties;
import com.oops.reasonaible.excuse.service.dto.AnthropicRequest;
import com.oops.reasonaible.excuse.service.dto.AnthropicResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(AnthropicProperties.class)
@Service
public class AnthropicService {

	@Qualifier("anthropicWebClient")
	private final WebClient webClient;

	private final AnthropicProperties anthropicProperties;

	// @Value("${anthropic.model}")
	private String model;

	// @Value("${anthropic.max-tokens}")
	private Integer maxTokens;

	// @Value("${anthropic.temperature}")
	private Double temperature;

	@PostConstruct
	public void init() {
		this.model = anthropicProperties.getModel();
		this.maxTokens = anthropicProperties.getMaxTokens();
		this.temperature = anthropicProperties.getTemperature();
	}

	public Mono<AnthropicResponse> generateExcuse(String content) {
		AnthropicRequest request = AnthropicRequest.create(
			model, AnthropicRequest.Message.user("다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: " + content),
			maxTokens, temperature);
		return webClient.post()
			.uri("/messages")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AnthropicResponse.class)
			.doOnError(WebClientResponseException.class, e -> log.error("Error: {}", e.getResponseBodyAsString()))
			;
	}
}
