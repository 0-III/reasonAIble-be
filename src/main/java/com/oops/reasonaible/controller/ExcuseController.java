package com.oops.reasonaible.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.service.ExcuseService;
import com.oops.reasonaible.service.dto.ApiResponse;
import com.oops.reasonaible.service.dto.ExcuseRequest;
import com.oops.reasonaible.service.dto.ExcuseResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/excuse")
@RestController
public class ExcuseController {

	private final ExcuseService excuseService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<ExcuseResponse>> createExcuse(
		@RequestBody ExcuseRequest excuseRequest) {
		ExcuseResponse excuse = excuseService.createExcuse(excuseRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(
			ApiResponse.of(201, excuse));
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<ExcuseResponse>>> getExcuse() {
		List<ExcuseResponse> allExcuses = excuseService.getAllExcuses();
		return ResponseEntity.status(HttpStatus.OK).body(
			ApiResponse.of(200, allExcuses));
		// return ResponseEntity.status(HttpStatus.OK).body(allExcuses);
	}

}
