package com.api.simulation.database.repository.nba;

import com.api.simulation.database.entity.nba.SeasonStanding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonStandingRepository extends JpaRepository<SeasonStanding, Long> {
}
