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
@Table(name = "team")
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
    @Column(name = "team_city", nullable = false, length = 100)
    private String teamCity;

    @Size(max = 10)
    @NotNull
    @Column(name = "team_code", nullable = false, length = 10)
    private String teamCode;

    @NotNull
    @Lob
    @Column(name = "conference", nullable = false)
    private String conference;

    @NotNull
    @Lob
    @Column(name = "division", nullable = false)
    private String division;

}
