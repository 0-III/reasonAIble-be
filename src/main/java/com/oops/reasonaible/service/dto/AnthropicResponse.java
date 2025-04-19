package com.oops.reasonaible.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnthropicResponse(
	String id,
	String type,
	String role,
	// List<Content> content,
	String content,
	String model,
	String stopReason,
	// String stopSequence,
	Usage usage
) {

	// public record Content(
	// 	String text,
	// 	String type
	// ) {
	// }

	public record Usage(
		int inputTokens,
		int outputTokens
	) {
	}

	// public static AnthropicResponse getClaudeError(String errorMessage) {
	// 	AnthropicResponse anthropicResponse = new AnthropicResponse();
	// 	Content content = new Content();
	// 	content.setText(errorMessage);
	// 	anthropicResponse.setType("error");
	// 	anthropicResponse.setContent(List.of(content));
	// 	return anthropicResponse;
	// }
}
