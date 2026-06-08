package com.api.simulation.service;

import com.api.simulation.database.entity.NbaTeam;
import com.api.simulation.database.entity.NbaTeamStats;
import com.api.simulation.database.repository.NbaTeamRepository;
import com.api.simulation.database.repository.NbaTeamStatsRepository;
import com.api.simulation.dto.NbaTeamRatingResponse;
import com.api.simulation.dto.NbaTeamResponse;
import com.api.simulation.prompts.NbaPrompts;
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
    private final NbaTeamRepository nbaTeamRepository;
    private final NbaTeamStatsRepository nbaTeamStatsRepository;

    public Object generateTeam(){

        NbaTeamResponse nbaTeamResponse =
                chatClient.prompt(NbaPrompts.GENERATE_TEAMS)
                        .call()
                        .entity(NbaTeamResponse.class);

        List<NbaTeam> nbaTeams = nbaTeamResponse.getTeams().stream()
                .map(t-> NbaTeam.builder()
                        .teamName(t.getName())
                        .teamCity(t.getCity())
                        .conferenceSide(t.getConferenceSide())
                        .build()
                    ).toList();

        nbaTeamRepository.saveAll(nbaTeams);

        return nbaTeamResponse.getTeams();
    }

    public ResponseEntity<Object> generateTeamStats() throws JsonProcessingException {

        List<NbaTeam> nbaTeams = nbaTeamRepository.findAll();

        if(nbaTeams.isEmpty())
            return ResponseEntity.badRequest().body("No teams found");

        String teamsJson = objectMapper.writeValueAsString(
                Map.of(
                        "teams",
                        nbaTeams.stream()
                                .map(team -> Map.of(
                                        "id", team.getId(),
                                        "city", team.getTeamCity(),
                                        "name", team.getTeamName(),
                                        "side", team.getConferenceSide()
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

        nbaTeamStatsRepository.saveAll(teamStats);

        return ResponseEntity.ok(response.getTeams());
    }
}
