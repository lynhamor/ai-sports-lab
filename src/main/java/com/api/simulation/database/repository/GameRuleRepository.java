package com.api.simulation.database.repository;

import com.api.simulation.database.entity.GameRule;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRuleRepository extends JpaRepository<GameRule, Long> {
    Optional<GameRule> findByGameIdAndRuleKey(@NotNull Long gameId, @Size(max = 100) @NotNull String ruleKey);

    Optional<GameRule> findByIdAndGameId(Long id, @NotNull Long gameId);
}
