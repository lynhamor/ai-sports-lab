package com.api.simulation.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "nba_team_stats")
public class NbaTeamStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @ColumnDefault("0.5000")
    @Column(name = "winRate", precision = 5, scale = 4)
    private BigDecimal winRate;

    @ColumnDefault("0.5000")
    @Column(name = "offensive_rate", precision = 5, scale = 4)
    private BigDecimal offensiveRate;

    @ColumnDefault("0.5000")
    @Column(name = "defensive_rate", precision = 5, scale = 4)
    private BigDecimal defensiveRate;

}
