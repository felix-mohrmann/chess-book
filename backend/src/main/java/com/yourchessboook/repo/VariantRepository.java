package com.yourchessboook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourchessboook.model.VariantEntity;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {
    void deleteByName(String name);
}
