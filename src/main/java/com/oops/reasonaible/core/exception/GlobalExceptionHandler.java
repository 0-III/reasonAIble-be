package com.oops.reasonaible.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oops.reasonaible.core.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ExceptionResponse> handleException(CommonException e) {
		ExceptionResponse response = ExceptionResponse.of(e.getStatusCode(), e.getMessage());
		return sendErrorResponse(response);
	}

	private ResponseEntity<ExceptionResponse> sendErrorResponse(ExceptionResponse response) {
		return ResponseEntity
			.status(response.code())
			.body(ExceptionResponse.of(response.code(), response.message()));
	}
}
