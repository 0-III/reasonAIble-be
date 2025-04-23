package com.oops.reasonaible.common.dto;

public record ExceptionResponse(
	Integer code,
	String message
) {

	public static ExceptionResponse of(Integer code, String message) {
		return new ExceptionResponse(code, message);
	}
}
