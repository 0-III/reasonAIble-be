package com.oops.reasonaible.service.dto;

import com.oops.reasonaible.entity.Excuse;

/**
 * DTO for {@link Excuse}
 */
public record ExcuseGenerationRequest(String situation) {

	public Excuse toEntity(String excuse) {
		return Excuse.of(situation, excuse);
	}
}
