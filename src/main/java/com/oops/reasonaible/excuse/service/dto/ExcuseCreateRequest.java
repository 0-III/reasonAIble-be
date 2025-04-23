package com.oops.reasonaible.excuse.service.dto;

import com.oops.reasonaible.excuse.entity.Excuse;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Excuse}
 */
public record ExcuseCreateRequest(String situation, @NotNull(message = "excuse 없음.") String excuse) {

	public Excuse toEntity() {
		return Excuse.of(situation, excuse);
	}
}
