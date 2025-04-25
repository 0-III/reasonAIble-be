package com.oops.reasonaible.member.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@ColumnDefault("'DEFAULT'")
	@Column(nullable = false, unique = true)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public static Member createUser(String email, String password, String nickname) {
		return new Member(
			null,
			email,
			password,
			nickname,
			Role.USER
		);
	}

	public static Member createAdmin(String email, String password) {
		return new Member(
			null,
			email,
			password,
			"admin",
			Role.ADMIN
		);
	}
}
