package com.oops.reasonaible.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oops.reasonaible.core.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ExceptionResponse> handleException(CommonException e) {
		return sendErrorResponse(e.getHttpStatus(), e.getMessage());
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
		return sendErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<?> handleServerException(Exception e) {
		HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
		return sendErrorResponse(internalServerError, internalServerError.getReasonPhrase());
	}

	private ResponseEntity<ExceptionResponse> sendErrorResponse(HttpStatus httpStatus, String message) {
		ExceptionResponse response = ExceptionResponse.of(httpStatus.value(), message);
		return ResponseEntity
			.status(response.code())
			.body(response);
	}
}
