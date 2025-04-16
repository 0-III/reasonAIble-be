package com.oops.reasonaible.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oops.reasonaible.service.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ExceptionResponse> handleException(CommonException e) {
		ExceptionResponse response = ExceptionResponse.of(e.getStatusCode(), e.getMessage());
		return sendErrorResponse(e.getStatusCode(), e.getMessage());
	}

	private ResponseEntity<ExceptionResponse> sendErrorResponse(Integer statusCode, String message) {
		return ResponseEntity
			.status(statusCode)
			.body(ExceptionResponse.of(statusCode, message));
	}
}
