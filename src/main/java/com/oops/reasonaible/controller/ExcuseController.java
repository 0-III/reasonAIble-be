package com.oops.reasonaible.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.service.ExcuseService;
import com.oops.reasonaible.service.dto.ApiResponse;
import com.oops.reasonaible.service.dto.ExcuseCreateRequest;
import com.oops.reasonaible.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.service.dto.ExcuseGetResponse;
import com.oops.reasonaible.service.dto.ExcuseUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/excuses")
@RestController
public class ExcuseController {

	private final ExcuseService excuseService;

	@PostMapping("")
	public ResponseEntity<ApiResponse> createExcuse(
		@RequestBody @Valid ExcuseCreateRequest excuseCreateRequest) {
		ExcuseCreateUpdateResponse excuse = excuseService.createExcuse(excuseCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			ApiResponse.of(201, excuse));
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<ExcuseGetResponse>>> getExcuses() {
		List<ExcuseGetResponse> allExcuses = excuseService.getAllExcuses();
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, allExcuses));
	}

	@GetMapping("/{excuse_id}")
	public ResponseEntity<ApiResponse<ExcuseGetResponse>> getExcuse(
		@RequestParam Long excuseId) {
		ExcuseGetResponse excuse = excuseService.getExcuse(excuseId);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, excuse));
	}

	@PatchMapping("/{excuse_id}")
	public ResponseEntity<ApiResponse<ExcuseCreateUpdateResponse>> updateExcuse(
		@RequestParam Long excuseId,
		@RequestBody @Valid ExcuseUpdateRequest excuseUpdateRequest) {
		ExcuseCreateUpdateResponse excuse = excuseService.updateExcuse(excuseId, excuseUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, excuse));
	}

	@DeleteMapping("/{excuse_id}")
	public ResponseEntity<ApiResponse> deleteExcuse(
		@RequestParam Long excuseId) {
		excuseService.deleteExcuse(excuseId);
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, "삭제 완료"));
	}

}
