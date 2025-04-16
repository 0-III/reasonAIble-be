package com.oops.reasonaible.service.dto;

import java.time.LocalDateTime;

import com.oops.reasonaible.entity.Excuse;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
public record ExcuseResponse(Long id, String excuse, String modifiedExcuse, LocalDateTime createdAt,
							 LocalDateTime updatedAt) {

	public static ExcuseResponse of(Long id, String excuse, String modifiedExcuse, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		return new ExcuseResponse(id, excuse, modifiedExcuse, createdAt, updatedAt);
	}

	public static ExcuseResponse from(Excuse excuse) {
		return new ExcuseResponse(excuse.getId(), excuse.getExcuse(), excuse.getModifiedExcuse(),
			excuse.getCreatedAt(), excuse.getUpdatedAt());
	}
}
