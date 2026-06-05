package com.api.simulation.database.repository;

import com.api.simulation.database.entity.NbaTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamStatsRepository extends JpaRepository<NbaTeamStats, Long> {
}
