package com.oops.reasonaible.excuse.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnthropicRequest(
	String model,
	List<Message> messages,
	Integer maxTokens,
	Double temperature
) {

	public static AnthropicRequest create(String model, Message message, Integer maxTokens, Double temperature) {
		return new AnthropicRequest(model, List.of(Message.user(message.content())), maxTokens, temperature);
	}

	public record Message(
		String role,
		String content
	) {
		public static Message user(String content) {
			return new Message("user", content);
		}
	}
}
