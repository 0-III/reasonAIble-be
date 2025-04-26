package com.oops.reasonaible.excuse.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AIResponse(
	// String id,
	// String type,
	// String role,
	List<Content> content
	// Content content
	// String model,
	// String stopReason,
	// String stopSequence,
	// Usage usage
) {

	public record Content(
		String text,
		String type
	) {
	}

	// public record Usage(
	// 	int inputTokens,
	// 	int outputTokens
	// ) {
	// }

	public static AIResponse of(List<Content> contents) {
		return new AIResponse(contents);
	}
}
