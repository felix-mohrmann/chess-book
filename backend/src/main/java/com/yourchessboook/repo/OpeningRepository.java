package de.yourchessboook.repo;

import de.yourchessboook.model.OpeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpeningRepository extends JpaRepository<OpeningEntity, Long> {
    Optional<OpeningEntity> findByName(String name);
}
