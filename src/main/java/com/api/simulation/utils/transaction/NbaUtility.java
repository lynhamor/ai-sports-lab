package com.api.simulation.utils.transaction;

import com.api.simulation.database.entity.nba.NbaTeam;
import com.api.simulation.database.entity.nba.NbaTeamStats;
import com.api.simulation.database.repository.nba.NbaTeamRepository;
import com.api.simulation.database.repository.nba.NbaTeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NbaUtility {

    private final NbaTeamRepository nbaTeamRepository;
    private final NbaTeamStatsRepository nbaTeamStatsRepository;

    public List<NbaTeam> saveAllTeams(List<NbaTeam> nbaTeams) {
        return nbaTeamRepository.saveAll(nbaTeams);
    }

    public List<NbaTeam> findAllTeams() {
        return nbaTeamRepository.findAll();
    }

    public List<NbaTeamStats> saveAllStats(List<NbaTeamStats> nbaTeamStats) {
        return nbaTeamStatsRepository.saveAll(nbaTeamStats);
    }
}