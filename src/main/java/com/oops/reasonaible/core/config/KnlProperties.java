package com.oops.reasonaible.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "knl")
public class KnlProperties {

	private final String baseUrl;
	private final String model;
	private final int maxTokens;
	private final double temperature;
}
