package com.oops.reasonaible.excuse.service;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.oops.reasonaible.core.config.AnthropicProperties;
import com.oops.reasonaible.core.exception.CommonException;
import com.oops.reasonaible.core.exception.ErrorCode;
import com.oops.reasonaible.excuse.entity.Excuse;
import com.oops.reasonaible.excuse.repository.ExcuseRepository;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(AnthropicProperties.class)
@Service
public class AnthropicService implements AIService {

	private final WebClient anthropicWebClient;
	private final AnthropicChatModel chatModel;
	private final ExcuseRepository excuseRepository;
	private final MemberRepository memberRepository;
	private final AnthropicProperties anthropicProperties;

	@Transactional
	@Override
	public ExcuseCreateUpdateResponse generateExcuse(String situation, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
		Prompt prompt = new Prompt(
			"다음 상황에 대한 변명을 상대방이 납득할만하게 어떻게 말할지 사족은 빼고 알려주세요: " + situation,
			AnthropicChatOptions.builder()
				.model(anthropicProperties.getModel())
				.temperature(anthropicProperties.getTemperature())
				.maxTokens(anthropicProperties.getMaxTokens())
				.build()
		);
		String response = chatModel.call(prompt).getResult().getOutput().getText().replace("\"", "");
		Excuse savedExcuse = excuseRepository.save(
			Excuse.of(situation, response, member)
		);
		return ExcuseCreateUpdateResponse.from(savedExcuse);
	}
}
