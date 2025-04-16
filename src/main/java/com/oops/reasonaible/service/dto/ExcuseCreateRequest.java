package com.oops.reasonaible.service.dto;

import com.oops.reasonaible.entity.Excuse;

/**
 * DTO for {@link Excuse}
 */
public record ExcuseCreateRequest(String excuse) {

	public Excuse toEntity() {
		return Excuse.of(excuse);
	}
}
