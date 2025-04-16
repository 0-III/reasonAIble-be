package com.oops.reasonaible.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND(404, "Not Found"),
	;

	private final Integer statusCode;
	private final String message;
}
