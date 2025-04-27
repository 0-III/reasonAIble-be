package com.oops.reasonaible.member.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oops.reasonaible.core.dto.ExceptionResponse;
import com.oops.reasonaible.core.exception.CommonException;
import com.oops.reasonaible.core.exception.ErrorCode;
import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.repository.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Map<String, List<String>> NO_CHECK_METHODS = Map.of(
		"/api/v1/login", List.of("POST"),
		"/api/v1/members", List.of("POST"),
		// "/api/v1/excuses", List.of("POST", "GET", "PATCH", "DELETE"),
		"/swagger-ui", List.of("GET"),
		"/v3/api-docs", List.of("GET")
	);

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	private final ObjectMapper objectMapper;
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String method = request.getMethod();

		// jwt 검증하지 않는 요청 처리
		if (NO_CHECK_METHODS.entrySet().stream()
			.anyMatch(entry -> requestURI.startsWith(entry.getKey()) && entry.getValue().contains(method))) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = jwtTokenProvider.extractAccessToken(request)
			.orElse(null);

		try {
			if (jwtTokenProvider.isTokenValid(accessToken)) {
				checkAccessTokenAndAuthentication(request, response, filterChain);
			} else {
				throw new CommonException(ErrorCode.INVALID_ACCESS_TOKEN);
			}
		} catch (CommonException e) {
			handleInvalidTokenException(response, e);
		}
	}

	private void handleInvalidTokenException(HttpServletResponse response, CommonException e)
		throws IOException {

		ResponseEntity<ExceptionResponse> errorResponse =
			ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ExceptionResponse.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		objectMapper.writeValue(
			response.getWriter(),
			errorResponse.getBody()
		);
	}

	/**
	 * AccessToken 검증 후 권한 정보 저장 및 인증
	 */
	private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		jwtTokenProvider.extractAccessToken(request)
			.filter(jwtTokenProvider::isTokenValid)
			.ifPresent(accessToken -> {
				jwtTokenProvider.extractEmail(accessToken)
					.ifPresent(email -> memberRepository.findByEmail(email)
						.ifPresent(this::saveAuthentication)
					);
				jwtTokenProvider.extractUserId(accessToken)
					.ifPresent(userId -> log.info("access token userId: {}", userId));
			});
		filterChain.doFilter(request, response);
	}

	/**
	 * AccessToken 인증 성공 이후, 해당 객체를 SecurityContextHolder에 담아 인증 처리
	 */
	private void saveAuthentication(Member member) {
		String password = member.getPassword();

		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
			.username(member.getEmail())
			.password(password)
			.roles(member.getRole().name())  // TODO: 권한 설정
			.build();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails, null, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
