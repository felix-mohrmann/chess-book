package com.yourchessboook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourchessboook.model.OpeningEntity;

import java.util.Optional;

public interface OpeningRepository extends JpaRepository<OpeningEntity, Long> {
    Optional<OpeningEntity> findByName(String name);
}
