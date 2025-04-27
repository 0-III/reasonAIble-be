package com.oops.reasonaible.excuse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.core.dto.ApiResponse;
import com.oops.reasonaible.excuse.service.ExcuseService;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateRequest;
import com.oops.reasonaible.excuse.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseGetResponse;
import com.oops.reasonaible.excuse.service.dto.ExcuseUpdateRequest;
import com.oops.reasonaible.member.login.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/excuses")
@RestController
public class ExcuseController {

	private final ExcuseService excuseService;

	@PostMapping("")
	public ResponseEntity<ApiResponse> createExcuse(
		@RequestBody @Valid ExcuseCreateRequest excuseCreateRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long memberId = userDetails.getMemberId();
		ExcuseCreateUpdateResponse excuse = excuseService.createExcuse(excuseCreateRequest, memberId);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			ApiResponse.of(201, excuse));
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<ExcuseGetResponse>>> getExcuses(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long memberId = userDetails.getMemberId();
		List<ExcuseGetResponse> allExcuses = excuseService.getAllExcuses(memberId);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, allExcuses));
	}

	@GetMapping("/{excuseId}")
	public ResponseEntity<ApiResponse<ExcuseGetResponse>> getExcuse(
		@PathVariable Long excuseId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long memberId = userDetails.getMemberId();
		ExcuseGetResponse excuse = excuseService.getExcuse(excuseId, memberId);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, excuse));
	}

	@PatchMapping("/{excuseId}")
	public ResponseEntity<ApiResponse<ExcuseCreateUpdateResponse>> updateExcuse(
		@PathVariable Long excuseId,
		@RequestBody @Valid ExcuseUpdateRequest excuseUpdateRequest) {
		ExcuseCreateUpdateResponse excuse = excuseService.updateExcuse(excuseId, excuseUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, excuse));
	}

	@DeleteMapping("/{excuseId}")
	public ResponseEntity<ApiResponse> deleteExcuse(
		@PathVariable Long excuseId) {
		excuseService.deleteExcuse(excuseId);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, "삭제 완료"));
	}

}
