package com.api.simulation.database.repository.nba;

import com.api.simulation.database.entity.nba.NbaMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NbaMatchRepository extends JpaRepository<NbaMatch, Long> {

    Optional<NbaMatch> findTopByOrderByIdDesc();
}
