package com.oops.reasonaible.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 리소스입니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 엑세스 토큰입니다.");;

	private final HttpStatus httpStatus;
	private final String message;
}
