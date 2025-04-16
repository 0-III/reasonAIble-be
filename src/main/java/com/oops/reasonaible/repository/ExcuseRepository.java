package com.oops.reasonaible.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oops.reasonaible.entity.Excuse;

public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
}
