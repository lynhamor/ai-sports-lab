package com.api.simulation.database.repository;

import com.api.simulation.database.entity.NbaTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamRepository extends JpaRepository<NbaTeam, Long> {
}
