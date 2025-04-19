package com.oops.reasonaible.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnthropicRequest(
	String model,
	List<Message> messages,
	int maxTokens
) {

	public record Message(
		String role,
		String content
	) {
	}
}
