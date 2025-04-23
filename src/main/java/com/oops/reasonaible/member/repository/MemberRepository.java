package com.oops.reasonaible.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oops.reasonaible.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
