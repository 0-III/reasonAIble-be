package com.oops.reasonaible.excuse.service;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.oops.reasonaible.core.config.AnthropicProperties;
import com.oops.reasonaible.excuse.entity.Excuse;
import com.oops.reasonaible.excuse.repository.ExcuseRepository;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(AnthropicProperties.class)
@Service
public class AnthropicService implements AIService {

	// @Qualifier("anthropicWebClient")
	private final WebClient anthropicWebClient;
	// private final ChatClient chatClient;
	private final AnthropicChatModel chatModel;

	private final ExcuseRepository excuseRepository;

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

	@Transactional
	@Override
	public ExcuseCreateUpdateResponse generateExcuse(String situation) {
		Prompt prompt = new Prompt(
			"다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: " + situation,
			AnthropicChatOptions.builder()
				.model("claude-3-haiku-20240307")
				.temperature(temperature)
				.maxTokens(maxTokens)
				.build()
		);
		ChatResponse response = chatModel.call(prompt);
		Excuse savedExcuse = excuseRepository.save(
			Excuse.of(situation, response.toString())
		);
		return ExcuseCreateUpdateResponse.from(savedExcuse);
	}
	// @Transactional
	// @Override
	// // public Mono<AnthropicResponse> generateExcuse(String content) {
	// public Mono<ExcuseCreateUpdateResponse> generateExcuse(String situation) {
	// 	AnthropicRequest request = AnthropicRequest.create(
	// 		model, AnthropicRequest.Message.user("다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: " + situation),
	// 		maxTokens, temperature);
	// 	return anthropicWebClient.post()
	// 		.uri("/messages")
	// 		.bodyValue(request)
	// 		.retrieve()
	// 		.bodyToMono(AnthropicResponse.class)
	// 		.timeout(Duration.ofSeconds(60))
	// 		.doOnError(WebClientResponseException.class,
	// 			e -> log.error("Error: {}", e.getResponseBodyAsString()))
	// 		.doOnError(e -> log.error("Unexpected error: ", e))
	// 		.map(excuse -> {
	// 			Excuse savedExcuse = excuseRepository.save(
	// 				Excuse.of(situation, excuse.content().get(0).text()));
	// 			return ExcuseCreateUpdateResponse.from(savedExcuse);
	// 		});
	// 	// .onErrorResume(e -> {
	// 	// 	String errorMessage = "Sorry, I couldn't process your request. " +
	// 	// 		"Please try again later.";
	// 	// 	return Mono.just(ExcuseCreateUpdateResponse.of(1L, errorMessage, situation));
	// 	// });
	// }
}
