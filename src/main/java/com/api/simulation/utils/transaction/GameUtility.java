package com.api.simulation.utils.transaction;

import com.api.simulation.database.entity.core.Game;
import com.api.simulation.database.entity.core.GameRule;
import com.api.simulation.database.repository.core.GameRepository;
import com.api.simulation.database.repository.core.GameRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * Utility component that provides common helper operations for Game and GameRule entities.
 *
 * <p>This class centralizes frequently used database retrieval and validation logic
 * to avoid duplication across services. It acts as a domain-level helper for
 * fetching, validating, and persisting game-related data.</p>
 *
 *
 * @author
 */
@Component
@RequiredArgsConstructor
public class GameUtility {

    private final GameRuleRepository gameRuleRepository;
    private final GameRepository gameRepository;

    /**
     * Retrieves a paginated list of games.
     *
     * @param pageable pagination information
     * @return paginated list of {@link Game}
     */
    public Page<Game> pageGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    /**
     * Retrieves a paginated list of game rules.
     *
     * @param pageable pagination information
     * @return paginated list of {@link GameRule}
     */
    public Page<Map<String, Object>> pageGameRules(Pageable pageable) {
        return gameRuleRepository.findAllGameRules(pageable);
    }

    /**
     * Retrieves a {@link Game} by its unique identifier.
     *
     * @param gameId the ID of the game to retrieve
     * @return the matching {@link Game}
     * @throws RuntimeException if no game is found with the given ID
     */
    public Game findGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    /**
     * Retrieves a {@link Game} by a partial or full game name match.
     *
     * @param gameName the name (or partial name) of the game to search for
     * @return the matching {@link Game}
     * @throws RuntimeException if no game is found matching the given name
     */
    public Game findGame(String gameName) {
        return gameRepository.findByGameNameContaining(gameName)
                .orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    /**
     * Retrieves a {@link GameRule} by its unique identifier.
     *
     * @param gameRuleId the ID of the game rule
     * @return the matching {@link GameRule}
     * @throws RuntimeException if no game rule is found with the given ID
     */
    public GameRule findGameRule(Long gameRuleId) {
        return gameRuleRepository.findById(gameRuleId)
                .orElseThrow(() -> new RuntimeException("Game Rule does not exist"));
    }

    /**
     * Retrieves a {@link GameRule} by its ID and associated Game ID.
     *
     * @param gameId the ID of the associated game
     * @return the matching {@link GameRule}
     * @throws RuntimeException if no matching game rule is found
     */
    public List<GameRule> findGameRules(Long gameId) {
        return gameRuleRepository.findByGameId( gameId)
                .orElseThrow(() -> new RuntimeException("Game Rule does not exist"));
    }

    public GameRule findGameRule(Long gameId, String ruleKey) {
        return gameRuleRepository.findByGameIdAndRuleKey(gameId, ruleKey)
                .orElseThrow(() -> new RuntimeException("Game Rule does not exist"));
    }

    /**
     * Checks whether a game rule already exists for a given game and rule key.
     *
     * @param gameId the ID of the game
     * @param ruleKey the rule key to check
     * @return {@code true} if the rule already exists, otherwise {@code false}
     */
    public boolean isExistingGameRule(Long gameId, String ruleKey) {
        return gameRuleRepository
                .findByGameIdAndRuleKey(gameId, ruleKey)
                .isPresent();
    }

    /**
     * Saves a {@link GameRule} entity to the database.
     *
     * @param gameRule the game rule entity to persist
     * @return the saved {@link GameRule}
     */
    public GameRule save(GameRule gameRule) {
        return gameRuleRepository.save(gameRule);
    }

    /**
     * Saves a {@link Game} entity to the database.
     *
     * @param game the game entity to persist
     * @return the saved {@link Game}
     */
    public Game save(Game game) {
        return gameRepository.save(game);
    }

}
