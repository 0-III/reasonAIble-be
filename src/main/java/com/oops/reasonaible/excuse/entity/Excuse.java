package com.oops.reasonaible.excuse.entity;

import com.oops.reasonaible.core.entity.BaseEntity;
import com.oops.reasonaible.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Excuse extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String situation;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String excuse;

	@Column(columnDefinition = "TEXT")
	private String modifiedExcuse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	protected Excuse() {
	}

	private Excuse(String situation, String excuse, String modifiedExcuse, Member member) {
		this.situation = situation;
		this.excuse = excuse;
		this.modifiedExcuse = modifiedExcuse;
		this.member = member;
	}

	public static Excuse of(String situation, String excuse, Member member) {
		return new Excuse(situation, excuse, excuse, member);
	}

	public void update(String modifiedExcuse) {
		this.modifiedExcuse = modifiedExcuse;
	}
}
