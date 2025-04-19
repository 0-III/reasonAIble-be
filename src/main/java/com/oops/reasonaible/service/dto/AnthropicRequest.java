package com.oops.reasonaible.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnthropicRequest(
	String model,
	List<Message> messages,
	// Double temperature,
	Integer max_tokens
) {

	public static AnthropicRequest create(String model, Message message) {
		return new AnthropicRequest(model, List.of(Message.user(message.content())), 100);
		// return new AnthropicRequest("claude-3-haiku-20240307", message, 0.7, 1000);
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
