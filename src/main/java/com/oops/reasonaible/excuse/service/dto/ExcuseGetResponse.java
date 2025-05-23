package com.oops.reasonaible.excuse.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oops.reasonaible.excuse.entity.Excuse;

/**
 * DTO for {@link Excuse}
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExcuseGetResponse(Long id, String situation, String excuse, String modifiedExcuse,
								LocalDateTime createdAt,
								LocalDateTime updatedAt) {

	public static ExcuseGetResponse from(Excuse excuse) {
		return new ExcuseGetResponse(excuse.getId(), excuse.getSituation(), excuse.getExcuse(),
			excuse.getModifiedExcuse(),
			excuse.getCreatedAt(), excuse.getUpdatedAt());
	}
}
