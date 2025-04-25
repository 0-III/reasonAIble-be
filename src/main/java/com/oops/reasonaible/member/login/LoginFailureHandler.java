package com.oops.reasonaible.member.login;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oops.reasonaible.core.dto.ExceptionResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		ResponseEntity<ExceptionResponse> loginFailureResponse =
			ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ExceptionResponse.of(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		objectMapper.writeValue(
			response.getWriter(),
			loginFailureResponse.getBody()
		);
	}
}
