package com.oops.reasonaible.excuse.service.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KnlRequest(
	String model,
	List<Message> messages,
	Integer maxCompletionTokens,
	Double temperature
) {

	public static KnlRequest create(String model, Message message, Integer maxTokens, Double temperature) {
		return new KnlRequest(model, List.of(Message.system(), Message.user(message.content())), maxTokens,
			temperature);
	}

	public record Message(
		String role,
		String content
	) {
		public static Message user(String content) {
			return new Message("user", content);
		}

		public static Message system() {
			return new Message("system",
				"당신은 코난테크놀로지에서 만든 AI Assistant입니다. 사용자의 요청에 언제나 정직하고 도움이 되도록 최선을 다해 응답해야 합니다.");
		}
	}
}
