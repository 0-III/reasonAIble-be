package com.oops.reasonaible.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class AnthropicRequest {
	private String model;
	private List<Message> messages;
	private int maxTokens;

	@Data
	public static class Message {
		private String role;
		private String content;
	}
}
