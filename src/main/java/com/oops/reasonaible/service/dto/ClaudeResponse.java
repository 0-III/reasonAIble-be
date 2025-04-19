package com.oops.reasonaible.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClaudeResponse {
	private List<Content> content;
	private String id;
	private String model;
	private String role;
	private String stopReason;
	private String stopSequence;
	private String type;
	private Usage usage;

	@Data
	public static class Content {
		private String text;
		private String type;
	}

	@Data
	public static class Usage {
		private int inputTokens;
		private int outputTokens;
	}

	public static ClaudeResponse getClaudeError(String errorMessage) {
		ClaudeResponse claudeResponse = new ClaudeResponse();
		Content content = new Content();
		content.setText(errorMessage);
		claudeResponse.setType("error");
		claudeResponse.setContent(List.of(content));
		return claudeResponse;
	}
}
