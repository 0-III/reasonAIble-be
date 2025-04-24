package com.oops.reasonaible.excuse.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oops.reasonaible.core.config.KnlProperties;
import com.oops.reasonaible.excuse.service.dto.AIResponse;
import com.oops.reasonaible.excuse.service.dto.AnthropicRequest;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(KnlProperties.class)
@Service
public class KnlService implements AIService {

	// @Qualifier("knlWebClient")
	private final WebClient knlWebClient;

	private final KnlProperties knlProperties;

	// @Value("${anthropic.model}")
	private String model;

	// @Value("${anthropic.max-tokens}")
	private Integer maxTokens;

	// @Value("${anthropic.temperature}")
	private Double temperature;

	@PostConstruct
	public void init() {
		this.model = knlProperties.getModel();
		this.maxTokens = knlProperties.getMaxTokens();
		this.temperature = knlProperties.getTemperature();
	}

	@Override
	public Mono<AIResponse> generateExcuse(String content) {
		// String prompt = "다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: ";
		String prompt = "다음 상황에 대한 변명 만들어주세요. 답변은 사족없이 변명으로 쓸 말만 써주세요. 말투는 친구한테 하는 말투로 써주세요.";
		AnthropicRequest request = AnthropicRequest.create(
			model, AnthropicRequest.Message.user(prompt + content),
			maxTokens, temperature);
		return knlWebClient.post()
			.uri("/messages")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(AIResponse.class)
			.doOnError(WebClientResponseException.class, e -> log.error("Error: {}", e.getResponseBodyAsString()))
			;
	}
}
