package com.api.simulation.service;

import com.api.simulation.constants.nba.Season;
import com.api.simulation.database.entity.core.Game;
import com.api.simulation.database.entity.core.GameRule;
import com.api.simulation.database.entity.nba.NbaTeam;
import com.api.simulation.database.entity.nba.NbaTeamStats;
import com.api.simulation.dto.nba.NbaTeamRatingResponse;
import com.api.simulation.dto.nba.NbaTeamResponse;
import com.api.simulation.prompts.NbaPrompts;
import com.api.simulation.utils.NbaMatchScheduler;
import com.api.simulation.utils.transaction.GameUtility;
import com.api.simulation.utils.transaction.NbaUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NbaService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final NbaUtility nbaUtility;
    private final NbaMatchScheduler nbaMatchScheduler;
    private final GameUtility gameUtility;

    public Object generateTeam(){

        NbaTeamResponse nbaTeamResponse =
                chatClient.prompt(NbaPrompts.GENERATE_TEAMS)
                        .call()
                        .entity(NbaTeamResponse.class);

        List<NbaTeam> nbaTeams = nbaTeamResponse.getTeams().stream()
                .map(t-> NbaTeam.builder()
                        .teamName(t.getName())
                        .teamCity(t.getCity())
                        .conference(t.getConference())
                        .division(t.getDivision())
                        .teamCode(t.getTeamCode())
                        .build()
                    ).toList();

        nbaUtility.saveAllTeams(nbaTeams);

        return nbaTeamResponse.getTeams();
    }

    public ResponseEntity<Object> generateTeamStats() throws JsonProcessingException {

        List<NbaTeam> nbaTeams = nbaUtility.findAllTeams();

        if(nbaTeams.isEmpty())
            return ResponseEntity.badRequest().body("No teams found");

        String teamsJson = objectMapper.writeValueAsString(
                Map.of(
                        "teams",
                        nbaTeams.stream()
                                .map(team -> Map.of(
                                        "id", team.getId(),
                                        "city", team.getTeamCity(),
                                        "name", team.getTeamName()
                                ))
                                .toList()
                )
        );

        String prompt = String.format(
                NbaPrompts.GENERATE_TEAM_STATS,
                teamsJson
        );

        NbaTeamRatingResponse response =  chatClient.prompt(prompt)
                .call()
                .entity(NbaTeamRatingResponse.class);

        List<NbaTeamStats> teamStats =
                response.getTeams().stream()
                        .map(dto -> NbaTeamStats.builder()
                                .teamId(dto.getId())
                                .winRate(dto.getWinRate())
                                .offensiveRate(dto.getOffensiveRate())
                                .defensiveRate(dto.getDefensiveRate())
                                .build())
                        .toList();

        nbaUtility.saveAllStats(teamStats);

        return ResponseEntity.ok(response.getTeams());
    }

    public ResponseEntity<Object> generateMatch(int limit){

        Game game = gameUtility.findGame("nba");
        List<GameRule> gameRules = gameUtility.findGameRules(game.getId());

        int year = gameRules.stream()
                .filter(gr -> "YEAR".equals(gr.getRuleKey()))
                .findFirst()
                .map(GameRule::getRuleValue)
                .map(Integer::parseInt)
                .orElseThrow(() -> new RuntimeException("YEAR rule not found"));

        Season season = gameRules.stream()
                .filter(gr -> "GAME_SEASON".equals(gr.getRuleKey()))
                .findFirst()
                .map(GameRule::getRuleValue)
                .map(Season::valueOf)
                .orElseThrow(() -> new RuntimeException("Season rule not found"));

        var matches = nbaMatchScheduler.generateBatch(season, year, limit);

        return ResponseEntity.ok(matches);
    }
}
