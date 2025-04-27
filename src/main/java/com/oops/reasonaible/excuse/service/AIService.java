package com.oops.reasonaible.excuse.service;

import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;

public interface AIService {

	// Mono<T> generateExcuse(String content);
	ExcuseCreateUpdateResponse generateExcuse(String content);
}
