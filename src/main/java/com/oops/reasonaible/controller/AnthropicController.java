package com.oops.reasonaible.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.service.ExcuseService;
import com.oops.reasonaible.service.dto.ApiResponse;
import com.oops.reasonaible.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.service.dto.ExcuseGenerationRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/v1/excuses")
@RestController
public class AnthropicController {

	private final ExcuseService excuseService;

	@PostMapping("/ai")
	public Mono<ResponseEntity<ApiResponse<ExcuseCreateUpdateResponse>>> generateExcuse(
		@RequestBody ExcuseGenerationRequest request
	) {
		return excuseService.generateExcuse(request.situation())
			.map(excuse -> {
				ExcuseCreateUpdateResponse response = new ExcuseCreateUpdateResponse(excuse.id(), request.situation(),
					excuse.excuse());
				return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), response));
			})
			// .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))))
			;
	}
}
