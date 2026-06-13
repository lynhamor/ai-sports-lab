package com.api.simulation.database.repository.core;

import com.api.simulation.database.entity.core.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGameNameContaining(String gameName);
}
