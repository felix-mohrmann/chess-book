package de.yourchessboook.repo;

import de.yourchessboook.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findAllByWhitePlayerIsOrderByOpening(String username);
    List<GameEntity> findAllByBlackPlayerIsOrderByOpening(String username);
}
