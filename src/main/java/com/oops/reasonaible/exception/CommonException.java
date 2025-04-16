package com.oops.reasonaible.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

	private final Integer statusCode;

	public CommonException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.statusCode = errorCode.getStatusCode();
	}
}
