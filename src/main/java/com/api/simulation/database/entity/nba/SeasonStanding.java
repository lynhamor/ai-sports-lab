package com.api.simulation.database.entity.nba;


import com.api.simulation.constants.nba.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match")
public class SeasonStanding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @NotNull
    @Column(name = "season_year", nullable = false)
    private Short seasonYear;

    @NotNull
    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @ColumnDefault("0")
    @Column(name = "total_match")
    private Integer totalMatch;

    @ColumnDefault("0")
    @Column(name = "total_win")
    private Integer totalWin;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "total_lose", nullable = false)
    private Integer totalLose;

    @ColumnDefault("0")
    @Column(name = "consecutive_win")
    private Integer consecutiveWin;

    @ColumnDefault("0")
    @Column(name = "consecutive_lose")
    private Integer consecutiveLose;

    @ColumnDefault("0.0000")
    @Column(name = "win_rate", precision = 5, scale = 4)
    private BigDecimal winRate;

}
