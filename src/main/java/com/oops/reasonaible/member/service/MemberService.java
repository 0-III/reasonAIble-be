package com.oops.reasonaible.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.repository.MemberRepository;
import com.oops.reasonaible.member.service.dto.RegisterRequest;
import com.oops.reasonaible.member.service.dto.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterResponse register(@Valid RegisterRequest request) {
		String nickname = request.nickname();
		if (nickname == null) {
			nickname = request.email().split("@")[0];
		}
		String encodedPassword = passwordEncoder.encode(request.password());
		Member member = Member.createUser(request.email(), encodedPassword, nickname);
		memberRepository.save(member);
		return RegisterResponse.of(member.getId(), member.getNickname());
	}
}
