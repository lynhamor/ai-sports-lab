package com.api.simulation.database.repository.nba;

import com.api.simulation.database.entity.nba.NbaTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamStatsRepository extends JpaRepository<NbaTeamStats, Long> {
}
