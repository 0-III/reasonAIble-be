package com.oops.reasonaible.service.dto;

import io.micrometer.common.lang.Nullable;

public record ApiResponse<T>(
	Integer code,
	@Nullable T data
) {

	public static <T> ApiResponse<T> of(Integer code, T data) {
		return new ApiResponse<>(code, data);
	}

}
