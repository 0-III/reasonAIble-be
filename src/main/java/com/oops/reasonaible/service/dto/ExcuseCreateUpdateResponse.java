package com.oops.reasonaible.service.dto;

import com.oops.reasonaible.entity.Excuse;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
public record ExcuseCreateUpdateResponse(Long id, String situation, String excuse) {

	public static ExcuseCreateUpdateResponse of(Long id, String situation, String excuse) {
		return new ExcuseCreateUpdateResponse(id, situation, excuse);
	}

	public static ExcuseCreateUpdateResponse from(Excuse savedExcuse) {
		return new ExcuseCreateUpdateResponse(
			savedExcuse.getId(),
			savedExcuse.getSituation(),
			savedExcuse.getModifiedExcuse()
		);
	}
}
