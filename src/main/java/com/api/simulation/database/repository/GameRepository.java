package com.api.simulation.database.repository;

import com.api.simulation.database.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
