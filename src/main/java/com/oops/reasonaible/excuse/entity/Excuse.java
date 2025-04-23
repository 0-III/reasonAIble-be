package com.oops.reasonaible.excuse.entity;

import com.oops.reasonaible.core.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	protected Excuse() {
	}

	private Excuse(String situation, String excuse, String modifiedExcuse) {
		this.situation = situation;
		this.excuse = excuse;
		this.modifiedExcuse = modifiedExcuse;
	}

	public static Excuse of(String situation, String excuse) {
		return new Excuse(situation, excuse, excuse);
	}

	public void update(String modifiedExcuse) {
		this.modifiedExcuse = modifiedExcuse;
	}
}
