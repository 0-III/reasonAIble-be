package com.oops.reasonaible.member.service;

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

	public RegisterResponse register(@Valid RegisterRequest request) {
		String nickname = request.nickname();
		if (nickname == null) {
			nickname = request.email().split("@")[0];
		}
		Member member = Member.create(request.email(), request.password(), nickname);
		memberRepository.save(member);
		return RegisterResponse.of(member.getId(), member.getNickname());
	}
}
