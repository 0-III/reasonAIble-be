package com.oops.reasonaible.excuse.service;

import com.oops.reasonaible.excuse.service.dto.AIResponse;

import reactor.core.publisher.Mono;

public interface AIService {

	Mono<AIResponse> generateExcuse(String content);
}
