package com.oops.reasonaible.member.login.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oops.reasonaible.member.login.CustomUserDetails;
import com.oops.reasonaible.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return CustomUserDetails.from(memberRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")));
	}
}
