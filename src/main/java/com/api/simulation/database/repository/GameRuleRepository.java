package com.api.simulation.database.repository;

import com.api.simulation.database.entity.GameRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRuleRepository extends JpaRepository<GameRule, Long> {
}
