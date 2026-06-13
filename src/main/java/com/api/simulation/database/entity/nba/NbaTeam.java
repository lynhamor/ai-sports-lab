package com.api.simulation.database.entity.nba;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "nba_team")
public class NbaTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "team_name", nullable = false, length = 100)
    private String teamName;

    @Size(max = 100)
    @NotNull
    @Column(name = "conference_side", nullable = false, length = 100)
    private String conferenceSide;

    @Size(max = 100)
    @NotNull
    @Column(name = "team_city", nullable = false, length = 100)
    private String teamCity;

}
