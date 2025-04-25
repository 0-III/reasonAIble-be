package com.oops.reasonaible.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oops.reasonaible.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
}
