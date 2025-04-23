package com.oops.reasonaible.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
