package com.oops.reasonaible.excuse.service;

import org.springframework.transaction.annotation.Transactional;

public interface AIService<T> {

	@Transactional
	T generateExcuse(String situation, Long memberId);
}
