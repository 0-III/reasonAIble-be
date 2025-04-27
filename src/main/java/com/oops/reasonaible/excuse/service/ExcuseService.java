package com.oops.reasonaible.excuse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oops.reasonaible.core.exception.CommonException;
import com.oops.reasonaible.core.exception.ErrorCode;
import com.oops.reasonaible.excuse.entity.Excuse;
import com.oops.reasonaible.excuse.repository.ExcuseRepository;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateRequest;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseGetResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseUpdateRequest;
import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExcuseService {

	private final AnthropicService anthropicService;
	private final ExcuseRepository excuseRepository;
	private final MemberRepository memberRepository;
	// private final KnlService knlService;

	@Transactional
	public ExcuseCreateUpdateResponse createExcuse(ExcuseCreateRequest excuseCreateRequest, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
		Excuse excuse = Excuse.of(excuseCreateRequest.situation(), excuseCreateRequest.excuse(), member);
		excuseRepository.save(excuse);
		return ExcuseCreateUpdateResponse.of(excuse.getId(), excuse.getSituation(), excuse.getExcuse());
	}

	public List<ExcuseGetResponse> getAllExcuses(Long memberId) {
		return excuseRepository.findByMemberId(memberId)
			.stream()
			.map(ExcuseGetResponse::from)
			.toList();
	}

	public ExcuseGetResponse getExcuse(Long excuseId, Long memberId) {
		Excuse excuse = excuseRepository.findById(excuseId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
		if (!excuse.getMember().getId().equals(memberId)) {
			throw new CommonException(ErrorCode.FORBIDDEN);
		}
		return ExcuseGetResponse.from(excuse);
	}

	@Transactional
	public void deleteExcuse(Long excuseId) {
		excuseRepository.deleteById(excuseId);
	}

	@Transactional
	public ExcuseCreateUpdateResponse updateExcuse(Long excuseId, ExcuseUpdateRequest excuseRequest) {
		Excuse excuse = excuseRepository.findById(excuseId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
		excuse.update(excuseRequest.modifiedExcuse());
		return ExcuseCreateUpdateResponse.of(excuse.getId(), excuse.getSituation(), excuse.getExcuse());
	}

	// @Transactional
	// public Mono<ExcuseCreateUpdateResponse> generateKnlExcuse(String situation) {
	// 	log.info("situation: {}", situation);
	// 	return knlService.generateExcuse(situation)
	// 		.map(excuse -> {
	// 			Excuse savedExcuse = excuseRepository.save(
	// 				Excuse.of(situation, excuse.choices().get(0).message().content()));
	// 			return ExcuseCreateUpdateResponse.from(savedExcuse);
	// 		});
	// }
}
