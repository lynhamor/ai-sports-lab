package com.api.simulation.database.entity.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_rule")
public class GameRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "rule_key", nullable = false, length = 100)
    private String ruleKey;

    @NotNull
    @Lob
    @Column(name = "rule_value", nullable = false)
    private String ruleValue;

    @NotNull
    @Column(name = "game_id", nullable = false)
    private Long gameId;

}
