package com.oops.reasonaible.service.dto;

import java.time.LocalDateTime;

import com.oops.reasonaible.entity.Excuse;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
public record ExcuseGetResponse(Long id, String excuse, String modifiedExcuse, LocalDateTime createdAt,
								LocalDateTime updatedAt) {

	public static ExcuseGetResponse from(Excuse excuse) {
		return new ExcuseGetResponse(excuse.getId(), excuse.getExcuse(), excuse.getModifiedExcuse(),
			excuse.getCreatedAt(), excuse.getUpdatedAt());
	}
}
