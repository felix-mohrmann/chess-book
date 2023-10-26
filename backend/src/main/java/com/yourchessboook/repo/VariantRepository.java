package de.yourchessboook.repo;

import de.yourchessboook.model.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {
    void deleteByName(String name);
}
