package com.oops.reasonaible.service.dto;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
public record ExcuseCreateUpdateResponse(Long id) {

	public static ExcuseCreateUpdateResponse of(Long id) {
		return new ExcuseCreateUpdateResponse(id);
	}
}
