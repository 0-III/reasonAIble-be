package com.oops.reasonaible.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oops.reasonaible.service.dto.AnthropicRequest;
import com.oops.reasonaible.service.dto.AnthropicResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnthropicService {

	@Qualifier("anthropicWebClient")
	private final WebClient webClient;

	@Value("${anthropic.model}")
	private String model;

	@Value("${anthropic.max-tokens}")
	private Integer maxTokens;

	@Value("${anthropic.temperature}")
	private Double temperature;

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
