package com.api.simulation.controller;

import com.api.simulation.dto.gameMngt.CreateGameDto;
import com.api.simulation.dto.gameMngt.GameFilterDto;
import com.api.simulation.dto.gameMngt.GameRuleFilterDto;
import com.api.simulation.service.GameManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Game Management", description = "APIs for managing games, rules, and status")
@RestController
@RequestMapping("/api/game/mangt")
@RequiredArgsConstructor
public class GameManagementController {

    private final GameManagementService gameManagementService;

    @Operation(
            summary = "Get Game List",
            description = "Retrieve paginated list of games based on filter criteria."
    )
    @PostMapping("/game-list")
    public ResponseEntity<Object> getGameList(GameFilterDto filterDto) {
        return gameManagementService.gameList(filterDto);
    }

    @Operation(
            summary = "Create Game",
            description = "Creates a new game with name and category"
    )
    @PostMapping
    public ResponseEntity<Object> newGame(
            @RequestBody CreateGameDto payload
    ){
        return gameManagementService.createGame(payload);
    }

    @Operation(
            summary = "Toggle Game Status",
            description = "Enable or disable a specific game."
    )
    @PostMapping("/{gameId}/toggle-game/{status}")
    public ResponseEntity<Object> toggleGameStatus(
            @PathVariable Long gameId,
            @PathVariable boolean status
    ) {
        return gameManagementService.toggleGameStatus(gameId, status);
    }

    @Operation(
            summary = "Update Game Rule",
            description = "Update existing rule value for a game."
    )
    @PatchMapping("/{gameRuleId}/game-rule")
    public ResponseEntity<Object> udpateGameRule(
            @PathVariable Long gameRuleId,
            @RequestParam String ruleValue
    ){
        return gameManagementService.updateGameRule(gameRuleId, ruleValue);
    }

    @Operation(
            summary = "Create New Game Rule",
            description = "Add a new rule for a specific game."
    )
    @PostMapping("/{gameId}/game-rule")
    public ResponseEntity<Object> newGameRule(
            @PathVariable Long gameId,
            @RequestParam String ruleKey,
            @RequestParam String ruleValue
    ){
        return gameManagementService.newGameRule(gameId, ruleKey, ruleValue);
    }

    @Operation(
            summary = "Get Game Rule List",
            description = "Retrieve list of game rules with filters."
    )
    @PostMapping("/gameRule-list")
    public ResponseEntity<Object> getGameRuleList(GameRuleFilterDto filterDto) {
        return gameManagementService.gameRuleList(filterDto);
    }

}
