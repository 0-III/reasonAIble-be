package com.oops.reasonaible.excuse.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.oops.reasonaible.excuse.entity.Excuse;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Excuse}
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExcuseUpdateRequest(@NotNull(message = "modifiedExcuse 없음.") String modifiedExcuse) {
}
