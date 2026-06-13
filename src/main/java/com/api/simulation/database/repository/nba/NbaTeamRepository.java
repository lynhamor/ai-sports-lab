package com.api.simulation.database.repository.nba;

import com.api.simulation.database.entity.nba.NbaTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamRepository extends JpaRepository<NbaTeam, Long> {
}
