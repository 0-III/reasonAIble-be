package com.oops.reasonaible.excuse.service.dto;

import com.oops.reasonaible.excuse.entity.Excuse;

/**
 * DTO for {@link Excuse}
 */
public record ExcuseGenerationRequest(String situation) {

	public Excuse toEntity(String excuse) {
		return Excuse.of(situation, excuse);
	}
}
