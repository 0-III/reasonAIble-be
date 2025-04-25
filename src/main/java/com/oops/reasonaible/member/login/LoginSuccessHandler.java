package com.oops.reasonaible.member.login;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oops.reasonaible.core.dto.ApiResponse;
import com.oops.reasonaible.member.jwt.JwtTokenProvider;
import com.oops.reasonaible.member.repository.MemberRepository;
import com.oops.reasonaible.member.service.dto.LoginResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		String email = extractEmail(authentication);
		memberRepository.findByEmail(email)
			.ifPresent(member -> {
				String accessToken = jwtTokenProvider.createAccessToken(
					email,
					member.getId()
				);

				// 헤더에 토큰 설정
				jwtTokenProvider.addAccessTokenToResponse(response, accessToken);

				ResponseEntity<ApiResponse<LoginResponse>> loginResponse = ResponseEntity.ok()
					.body(ApiResponse.of(HttpStatus.OK.value(), LoginResponse.of(email)));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				try {
					objectMapper.writeValue(
						response.getWriter(),
						loginResponse.getBody()
					);
				} catch (IOException e) {
					log.error("login response body error", e);
				}
			});
	}

	private String extractEmail(Authentication authentication) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
