package com.api.simulation.utils.transaction;

import com.api.simulation.database.entity.nba.NbaMatch;
import com.api.simulation.database.entity.nba.NbaTeam;
import com.api.simulation.database.entity.nba.NbaTeamStats;
import com.api.simulation.database.entity.nba.SeasonStanding;
import com.api.simulation.database.repository.nba.NbaMatchRepository;
import com.api.simulation.database.repository.nba.NbaTeamRepository;
import com.api.simulation.database.repository.nba.NbaTeamStatsRepository;
import com.api.simulation.database.repository.nba.SeasonStandingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NbaUtility {

    private final NbaTeamRepository nbaTeamRepository;
    private final NbaTeamStatsRepository nbaTeamStatsRepository;
    private final NbaMatchRepository nbaMatchRepository;
    private final SeasonStandingRepository seasonStandingRepository;

    public List<NbaTeam> saveAllTeams(List<NbaTeam> nbaTeams) {
        return nbaTeamRepository.saveAll(nbaTeams);
    }

    public List<NbaTeam> findAllTeams() {
        return nbaTeamRepository.findAll();
    }

    public List<NbaTeamStats> saveAllStats(List<NbaTeamStats> nbaTeamStats) {
        return nbaTeamStatsRepository.saveAll(nbaTeamStats);
    }

    public List<NbaMatch> saveAllMatches(List<NbaMatch> nbaMatches) {
        return nbaMatchRepository.saveAll(nbaMatches);
    }

    public Page<NbaMatch> pageMatches(Pageable pageable) {
        return nbaMatchRepository.findAll(pageable);
    }

    public SeasonStanding saveSeasonStanding(SeasonStanding seasonStanding) {
        return seasonStandingRepository.save(seasonStanding);
    }

    public Page<SeasonStanding> pageStandings(Pageable pageable) {
        return seasonStandingRepository.findAll(pageable);
    }

    public NbaMatch findLastMatch() {
        return nbaMatchRepository.findTopByOrderByIdDesc().orElse(null);
    }
}