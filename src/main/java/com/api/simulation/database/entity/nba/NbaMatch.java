package com.api.simulation.database.entity.nba;

import com.api.simulation.constants.nba.GameEvent;
import com.api.simulation.constants.nba.GameStatus;
import com.api.simulation.constants.nba.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_event")
public class NbaMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Enumerated(EnumType.STRING)
    @Column(name = "event")
    private GameEvent event;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status")
    private GameStatus gameStatus;

    @NotNull
    @Column(name = "game_idex", nullable = false)
    private Integer gameIdex;

    @NotNull
    @Column(name = "home_team", nullable = false)
    private Long homeTeam;

    @NotNull
    @Column(name = "away_team", nullable = false)
    private Long awayTeam;

    @Column(name = "scheduled_at")
    private Date scheduledAt;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "ended_at")
    private Date endedAt;

    @NotNull
    @Lob
    @Column(name = "seed", nullable = false)
    private long seed;

    @NotNull
    @Column(name = "season_year", nullable = false)
    private Integer seasonYear;

}
