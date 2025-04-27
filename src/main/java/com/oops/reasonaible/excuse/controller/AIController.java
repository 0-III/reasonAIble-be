package com.oops.reasonaible.excuse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.core.dto.ApiResponse;
import com.oops.reasonaible.excuse.service.AnthropicService;
import com.oops.reasonaible.excuse.service.ExcuseService;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseGenerationRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/excuses")
@RestController
public class AIController {

	private final ExcuseService excuseService;
	private final AnthropicService anthropicService;

	@PostMapping("/ai")
	public ResponseEntity<ApiResponse<ExcuseCreateUpdateResponse>> generateExcuse(
		@RequestBody ExcuseGenerationRequest request
	) {
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(),
			anthropicService.generateExcuse(request.situation())));
	}

	// @PostMapping("/knl-ai")
	// public Mono<ResponseEntity<ApiResponse<ExcuseCreateUpdateResponse>>> generateKnlExcuse(
	// 	@RequestBody ExcuseGenerationRequest request
	// ) {
	// 	return excuseService.generateKnlExcuse(request.situation())
	// 		.map(excuse -> {
	// 			ExcuseCreateUpdateResponse response = new ExcuseCreateUpdateResponse(excuse.id(), request.situation(),
	// 				excuse.excuse());
	// 			return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), response));
	// 		})
	// 		// .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))))
	// 		;
	// }
}
