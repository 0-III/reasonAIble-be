package com.oops.reasonaible.excuse.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oops.reasonaible.core.config.KnlProperties;
import com.oops.reasonaible.core.exception.CommonException;
import com.oops.reasonaible.core.exception.ErrorCode;
import com.oops.reasonaible.excuse.entity.Excuse;
import com.oops.reasonaible.excuse.repository.ExcuseRepository;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.excuse.service.dto.KnlRequest;
import com.oops.reasonaible.excuse.service.dto.KnlResponse;
import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.repository.MemberRepository;

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
	private final ExcuseRepository excuseRepository;
	private final MemberRepository memberRepository;

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

	@Transactional
	@Override
	public Mono<ExcuseCreateUpdateResponse> generateExcuse(String situation, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
		// String prompt = "다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: ";
		String prompt = "다음 상황에 대한 변명 만들어주세요. 답변은 사족없이 변명으로 쓸 말만 써주세요. 말투는 친구한테 하는 말투로 써주세요.";
		KnlRequest request = KnlRequest.create(
			model, KnlRequest.Message.user(prompt + situation),
			maxTokens, temperature);
		Mono<KnlResponse> knlResponse = knlWebClient.post()
			.bodyValue(request)
			.retrieve()
			.bodyToMono(KnlResponse.class)
			.doOnError(WebClientResponseException.class, e -> log.error("Error: {}", e.getResponseBodyAsString()));
		return knlResponse
			.map(response -> {
				String excuse = response.choices().get(0).message().content();
				Excuse savedExcuse = excuseRepository.save(
					Excuse.of(situation, excuse, member));
				return ExcuseCreateUpdateResponse.from(savedExcuse);
			});
	}
}
