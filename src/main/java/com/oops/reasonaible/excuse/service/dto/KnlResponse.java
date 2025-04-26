package com.oops.reasonaible.excuse.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KnlResponse(
	// String id,
	// String type,
	// String role,
	List<Choice> choices
	// Content content
	// String model,
	// String stopReason,
	// String stopSequence,
	// Usage usage
) implements AIResponse {

	public record Choice(
		Message message
	) {
	}

	public record Message(
		String content
	) {
	}

	// public record Usage(
	// 	int inputTokens,
	// 	int outputTokens
	// ) {
	// }

	public static KnlResponse of(List<Choice> choices) {
		return new KnlResponse(choices);
	}
}
