package com.api.simulation.service;

import com.api.simulation.database.entity.Game;
import com.api.simulation.database.entity.GameRule;
import com.api.simulation.dto.PageDto;
import com.api.simulation.dto.gameMngt.GameFilterDto;
import com.api.simulation.dto.gameMngt.GameRuleFilterDto;
import com.api.simulation.utlis.transaction.GameUtility;
import com.api.simulation.utlis.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameManagementService {

    private final GameUtility gameUtility;

    /**
     * Retrieves a paginated list of games based on filter criteria.
     *
     * @param filterDto contains pagination and sorting parameters
     * @return paginated list of {@link Game} wrapped in a ResponseEntity
     */
    public ResponseEntity<Object> gameList(
            GameFilterDto filterDto
    ){

        int page = Math.max(filterDto.getPage() - 1, 0);
        Sort sort = SortUtil.build(filterDto);
        Pageable pageable = PageRequest.of(page, filterDto.getSize(), sort);

        Page<Game> pageResult = gameUtility.pageGames(pageable);
        PageDto<Game> pageDto = new PageDto<>(pageResult);

        return ResponseEntity.ok(pageDto);
    }

    /**
     * Retrieves a paginated list of game rules based on filter criteria.
     *
     * @param filterDto contains pagination and sorting parameters
     * @return paginated list of {@link GameRule} wrapped in a ResponseEntity
     */
    public ResponseEntity<Object> gameRuleList(
            GameRuleFilterDto filterDto
    ){

        int page = Math.max(filterDto.getPage() - 1, 0);
        Sort sort = SortUtil.build(filterDto);
        Pageable pageable = PageRequest.of(page, filterDto.getSize(), sort);

        Page<GameRule> pageResult = gameUtility.pageGameRules(pageable);
        PageDto<GameRule> pageDto = new PageDto<>(pageResult);

        return ResponseEntity.ok(pageDto);
    }

    /**
     * Creates a new game rule for a specific game.
     *
     * <p>This method validates if the game exists, checks whether a rule with the
     * same key already exists for the given game, and if not, creates and saves
     * a new {@link GameRule} entry.</p>
     *
     * @param gameId     the ID of the game where the rule will be applied
     * @param ruleKey    the unique key identifying the rule (e.g., "TOTAL_OFF_POSESSION")
     * @param ruleValue  the value of the rule (e.g., probability modifier or multiplier)
     *
     * @return ResponseEntity containing a success message when the rule is created
     *
     * @throws RuntimeException if the game does not exist
     * @throws RuntimeException if a rule with the same key already exists for the game
     */
    public ResponseEntity<Object> newGameRule(
            Long gameId,
            String ruleKey,
            String ruleValue
    ) {

        Game game = gameUtility.findGame(gameId);

        boolean exists = gameUtility.isExistingGameRule(game.getId(), ruleKey);

        if (exists) {
            throw new RuntimeException("Game Rule already exists");
        }

        GameRule gameRule = new GameRule();
        gameRule.setGameId(game.getId());
        gameRule.setRuleKey(ruleKey);
        gameRule.setRuleValue(ruleValue);

        gameUtility.save(gameRule);

        return ResponseEntity.ok("New Game Rule created");
    }

    /**
     * Updates an existing game rule for a specific game.
     *
     * <p>Finds the game rule by gameId and ruleKey, updates its value,
     * and saves the modified entity.</p>
     *
     * @param gameRuleId    the ID of the game rule
     * @param ruleValue     new value to set
     *
     * @return ResponseEntity with success message
     *
     * @throws RuntimeException if the game does not exist
     * @throws RuntimeException if the game rule does not exist
     */
    public ResponseEntity<Object> updateGameRule(
            Long gameRuleId,
            String ruleValue
    ) {

        GameRule gameRule = gameUtility.findGameRule(gameRuleId);
        gameRule.setRuleValue(ruleValue);

        gameUtility.save(gameRule);

        return ResponseEntity.ok("Game Rule updated");
    }

    /**
     * Toggles the enabled status of a Game.
     *
     * <p>This method retrieves a game by its ID, updates its enabled status,
     * and persists the changes to the database.</p>
     *
     * @param gameId the unique identifier of the game to be updated
     * @param status the new enabled status of the game
     *               {@code true} to enable the game, {@code false} to disable it
     * @return a {@link ResponseEntity} containing a success message indicating
     *         that the game status has been updated
     * @throws RuntimeException if the game with the given ID does not exist
     */
    public ResponseEntity<Object> toggleGameStatus(Long gameId, Boolean status) {

        Game game = gameUtility.findGame(gameId);
        game.setIsEnable(status);
        gameUtility.save(game);

        return ResponseEntity.ok("Game Status updated");
    }


}
