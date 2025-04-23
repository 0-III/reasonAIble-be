package com.oops.reasonaible.member.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link com.oops.reasonaible.member.entity.Member}
 */
public record RegisterRequest(@NotNull @Email String email, @NotNull String password, String nickname) {
}
