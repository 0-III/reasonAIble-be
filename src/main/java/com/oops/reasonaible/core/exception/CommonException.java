package com.oops.reasonaible.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

	private final HttpStatus httpStatus;

	public CommonException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.httpStatus = errorCode.getHttpStatus();
	}
}
