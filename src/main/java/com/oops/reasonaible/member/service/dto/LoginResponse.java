package com.oops.reasonaible.member.service.dto;

public record LoginResponse(
	String email
) {
	public static LoginResponse of(String email) {
		return new LoginResponse(email);
	}
}
