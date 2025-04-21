package com.oops.reasonaible.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExcuseUpdateRequest(@NotNull(message = "modifiedExcuse 없음.") String modifiedExcuse) {
}
