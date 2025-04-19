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

	// public Mono<AnthropicResponse> generateExcuse(AnthropicRequest request) {
	public Mono<AnthropicResponse> generateExcuse(String content) {
		AnthropicRequest request = AnthropicRequest.create(
			model, AnthropicRequest.Message.user("다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: " + content));
		return webClient.post()
			.uri("/messages")
			.bodyValue(request)
			// .bodyValue(Map.of(
			// 	"model", model,
			// 	"message", AnthropicRequest.Message.user(
			// 		// "role", "user",
			// 		"다음 상황에 대한 변명을 만들어주세요: " + request.message().content()
			// 	)
			// 	// "temperature", request.temperature(),
			// 	// "max_tokens", request.maxTokens()
			// ))
			.retrieve()
			.bodyToMono(AnthropicResponse.class)
			// .map(response -> response.content().text())
			.doOnError(WebClientResponseException.class, e -> log.error("Error: {}", e.getResponseBodyAsString()))
			;
	}

	// public Mono<AnthropicResponse> sendMessage(String message) {
	// 	return sendMessage(message, model, 1000, 0.7);
	// }

	// public Mono<AnthropicResponse> sendMessage(String message, String model, int maxTokens, double temperature) {
	// 	List<Message> message = Arrays.asList(Message.user(message));
	// 	AnthropicRequest anthropicRequest = AnthropicRequest.of(model, message, maxTokens);
	// 	return sendRequest(anthropicRequest);
	// }

	// private Mono<AnthropicResponse> sendRequest(AnthropicRequest anthropicRequest) {
	// 	return webClient.post()
	// 		.uri("")
	// 		.bodyValue(anthropicRequest)
	// 		.retrieve()
	// 		.bodyToMono(AnthropicResponse.class)
	// 		.doOnSuccess(response -> log.info("Response: {}", response.id()))
	// 		.doOnError(error -> log.error("Error: {}", error.getMessage()));
	// }

	// public Mono<String> generateText(String message) {
	// 	return sendMessage(message).map(AnthropicResponse::content);
	// }

}
