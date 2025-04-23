package com.oops.reasonaible.excuse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oops.reasonaible.excuse.entity.Excuse;

public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
}
