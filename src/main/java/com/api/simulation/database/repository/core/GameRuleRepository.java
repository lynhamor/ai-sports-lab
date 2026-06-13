package com.api.simulation.database.repository.core;

import com.api.simulation.database.entity.core.GameRule;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Optional;

public interface GameRuleRepository extends JpaRepository<GameRule, Long> {
    Optional<GameRule> findByGameIdAndRuleKey(@NotNull Long gameId, @Size(max = 100) @NotNull String ruleKey);

    Optional<GameRule> findByIdAndGameId(Long id, @NotNull Long gameId);

    @Query("""
        SELECT
             gr.id          AS id
           , gr.gameId      AS gameId
           , g.gameName     AS gameName
           , gr.ruleKey     AS ruleKey
           , gr.ruleValue   AS ruleValue
        FROM
            GameRule gr
        LEFT JOIN
            Game g
            ON gr.gameId = g.id
    """)
    Page<Map<String, Object>> findAllGameRules(Pageable pageable);
}
