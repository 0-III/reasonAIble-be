package com.oops.reasonaible.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oops.reasonaible.common.dto.ApiResponse;
import com.oops.reasonaible.member.service.MemberService;
import com.oops.reasonaible.member.service.dto.RegisterRequest;
import com.oops.reasonaible.member.service.dto.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
		RegisterResponse response = memberService.register(request);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK.value(), response));
	}
}
