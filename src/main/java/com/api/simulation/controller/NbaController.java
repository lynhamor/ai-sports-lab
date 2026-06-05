package com.api.simulation.controller;

import com.api.simulation.service.NbaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "NBA Generator",
        description = "Endpoints for generating NBA teams and team statistics data."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class NbaController {

    private final NbaService nbaService;

    @Operation(
            summary = "Generate NBA Teams",
            description = """
                    Generates a full list of NBA teams using AI simulation.

                    Each team contains:
                    - Team name
                    - City
                    - Conference side (east/west)

                    The generated teams will be persisted into the database.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "NBA teams generated successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error while generating teams"
            )
    })
    @GetMapping("/team")
    public ResponseEntity<Object> generateTeam() throws JsonProcessingException {

        return ResponseEntity.ok(nbaService.generateTeam());
    }

    @Operation(
            summary = "Generate NBA Team Statistics",
            description = """
                    Generates statistical data for NBA teams.

                    Response may include:
                    - Win rate
                    - Wins
                    - Losses
                    - Ranking
                    - Offensive and defensive metrics
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "NBA team statistics generated successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to generate NBA team statistics")
    })
    @GetMapping("/team-stats")
    public ResponseEntity<Object> generateTeamStats() throws JsonProcessingException {

        return nbaService.generateTeamStats();
    }
}
