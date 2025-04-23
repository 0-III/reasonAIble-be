package com.oops.reasonaible.member.service.dto;

/**
 * DTO for {@link com.oops.reasonaible.member.entity.Member}
 */
public record RegisterResponse(Long id, String nickname) {
	public static RegisterResponse of(Long id, String nickname) {
		return new RegisterResponse(id, nickname);
	}
}
