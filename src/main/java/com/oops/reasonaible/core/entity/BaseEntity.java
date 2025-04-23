package com.oops.reasonaible.core.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;
}
