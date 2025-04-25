package com.oops.reasonaible.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private final String secretKey;
	private final AccessTokenProperties access;
	private final RefreshTokenProperties refresh;

	@Getter
	@RequiredArgsConstructor
	public static class AccessTokenProperties {
		private final Long expiration;
		private final String header;
	}

	@Getter
	@RequiredArgsConstructor
	public static class RefreshTokenProperties {
		private final Long expiration;
		private final String header;
	}
}
