package com.oops.reasonaible.service.dto;

import com.oops.reasonaible.entity.Excuse;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Excuse}
 */
public record ExcuseRequest(@NotNull String excuse, String modifiedExcuse) {

	public static ExcuseRequest of(String excuse, String modifiedExcuse) {
		return new ExcuseRequest(excuse, modifiedExcuse);
	}

	public Excuse toEntity() {
		return Excuse.of(excuse, modifiedExcuse);
	}
}
