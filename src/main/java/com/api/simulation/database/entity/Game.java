package com.api.simulation.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "game_name", nullable = false, length = 100)
    private String gameName;

    @Size(max = 100)
    @NotNull
    @Column(name = "game_category", nullable = false, length = 100)
    private String gameCategory;

    @ColumnDefault("1")
    @Column(name = "is_enable")
    private Boolean isEnable;

}
