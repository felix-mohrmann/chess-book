package com.yourchessboook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourchessboook.model.GameEntity;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findAllByWhitePlayerIsOrderByOpening(String username);

    List<GameEntity> findAllByBlackPlayerIsOrderByOpening(String username);
}
