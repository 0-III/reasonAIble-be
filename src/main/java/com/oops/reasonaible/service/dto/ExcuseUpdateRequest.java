package com.oops.reasonaible.service.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link com.oops.reasonaible.entity.Excuse}
 */
public record ExcuseUpdateRequest(@NotNull String modifiedExcuse) {
}
