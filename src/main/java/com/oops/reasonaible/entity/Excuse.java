package com.oops.reasonaible.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Excuse {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String excuse;

	private String modifiedExcuse;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	protected Excuse() {
	}

	private Excuse(String excuse, String modifiedExcuse) {
		this.excuse = excuse;
		this.modifiedExcuse = modifiedExcuse;
	}

	public static Excuse of(String excuse) {
		return new Excuse(excuse, excuse);
	}

	public void update(String modifiedExcuse) {
		this.modifiedExcuse = modifiedExcuse;
	}
}
