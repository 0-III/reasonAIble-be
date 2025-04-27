package com.oops.reasonaible.member.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oops.reasonaible.member.entity.Member;
import com.oops.reasonaible.member.entity.Role;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	private Member user;

	private CustomUserDetails(Member member) {
		this.user = member;
	}

	public static CustomUserDetails from(Member member) {
		return new CustomUserDetails(member);
	}

	private GrantedAuthority getAuthority(Role role) {
		return new SimpleGrantedAuthority("ROLE_" + role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<>();

		switch (user.getRole()) {
			case ADMIN:
				authorityList.add(getAuthority(Role.ADMIN));
			case USER:
				authorityList.add(getAuthority(Role.USER));
		}
		return authorityList;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	public Long getMemberId() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
