package com.oops.reasonaible.excuse.service;

import org.springframework.transaction.annotation.Transactional;

import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;

public interface AIService {

	// Mono<T> generateExcuse(String content);
	// ExcuseCreateUpdateResponse generateExcuse(String content);

	@Transactional
	ExcuseCreateUpdateResponse generateExcuse(String situation, Long memberId);
}
