package com.oops.reasonaible.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oops.reasonaible.member.jwt.JwtAuthenticationFilter;
import com.oops.reasonaible.member.jwt.JwtTokenProvider;
import com.oops.reasonaible.member.login.LoginFailureHandler;
import com.oops.reasonaible.member.login.LoginSuccessHandler;
import com.oops.reasonaible.member.login.filter.CustomAuthenticationProcessingFilter;
import com.oops.reasonaible.member.login.service.CustomUserDetailsService;
import com.oops.reasonaible.member.repository.MemberRepository;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	private final ObjectMapper objectMapper;
	private final MemberRepository memberRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
				// .requestMatchers("/api/v1/members", "/api/v1/login").permitAll()
				//                        .anyRequest().authenticated() // TODO: 나중에 인증 필요로 변경
				.anyRequest().permitAll()
			)
			.addFilterAfter(customAuthenticationProcessingFilter(), LogoutFilter.class)
			.addFilterBefore(jwtAuthenticationFilter(),
				CustomAuthenticationProcessingFilter.class
			)
		;
		return http.build();
	}

	@Bean
	public Filter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, memberRepository, objectMapper);
	}

	@Bean
	public Filter customAuthenticationProcessingFilter() {
		CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter
			= new CustomAuthenticationProcessingFilter(objectMapper);
		customAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager());
		customAuthenticationProcessingFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
		customAuthenticationProcessingFilter.setAuthenticationFailureHandler(loginFailureHandler());
		return customAuthenticationProcessingFilter;
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler(objectMapper);
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler(jwtTokenProvider, memberRepository, objectMapper);
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(customUserDetailsService);
		return new ProviderManager(provider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
