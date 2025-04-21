package com.oops.reasonaible.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${client.url}")
	private String clientUrl;
	@Value("${client.url2}")
	private String clientUrl2;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins(clientUrl)
			.allowedOrigins(clientUrl2)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
			.allowedHeaders("*")
			.allowCredentials(true);
	}
}
