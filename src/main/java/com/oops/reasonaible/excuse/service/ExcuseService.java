package com.oops.reasonaible.excuse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oops.reasonaible.exception.CommonException;
import com.oops.reasonaible.exception.ErrorCode;
import com.oops.reasonaible.excuse.entity.Excuse;
import com.oops.reasonaible.excuse.repository.ExcuseRepository;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateRequest;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseGetResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExcuseService {

	private final AnthropicService anthropicService;
	private final ExcuseRepository excuseRepository;

	@Transactional
	public ExcuseCreateUpdateResponse createExcuse(ExcuseCreateRequest excuseCreateRequest) {
		Excuse excuse = excuseCreateRequest.toEntity();
		excuseRepository.save(excuse);
		return ExcuseCreateUpdateResponse.of(excuse.getId(), excuse.getSituation(), excuse.getExcuse());
	}

	public List<ExcuseGetResponse> getAllExcuses() {
		return excuseRepository.findAll()
			.stream()
			.map(ExcuseGetResponse::from)
			.toList();
	}

	public ExcuseGetResponse getExcuse(Long excuseId) {
		return excuseRepository.findById(excuseId)
			.map(ExcuseGetResponse::from)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
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

	@Transactional
	public Mono<ExcuseCreateUpdateResponse> generateExcuse(String situation) {
		log.info("situation: {}", situation);
		return anthropicService.generateExcuse(situation)
			.map(excuse -> {
				Excuse savedExcuse = excuseRepository.save(
					Excuse.of(situation, excuse.content().get(0).text()));
				return ExcuseCreateUpdateResponse.from(savedExcuse);
			});
	}
}
