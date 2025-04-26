package com.oops.reasonaible.excuse.service;

import com.oops.reasonaible.excuse.service.dto.AIResponse;

import reactor.core.publisher.Mono;

public interface AIService<T extends AIResponse> {

	Mono<T> generateExcuse(String content);
}
