package com.api.simulation.utils;

import com.api.simulation.constants.nba.GameEvent;
import com.api.simulation.constants.nba.GameStatus;
import com.api.simulation.constants.nba.Season;
import com.api.simulation.database.entity.nba.NbaMatch;
import com.api.simulation.database.entity.nba.NbaTeam;
import com.api.simulation.utils.transaction.NbaUtility;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NbaMatchScheduler {

    private final NbaUtility nbaUtility;
    private final ReturnTypeParser returnTypeParser;

    public record MatchRule(Long homeTeamId, Long awayTeamId, int games ) {}
    public List<NbaMatch> generateBatch(
            Season season,
            int year,
            int limit
    ) {

        long baseSeed = NbaSeedUtil.seasonSeed(season, year);
        long scheduleSeed = NbaSeedUtil.scheduleSeed(baseSeed);

        List<NbaTeam> teams = nbaUtility.findAllTeams();

        List<MatchRule> rules = buildRules(teams, scheduleSeed);

        int lastIndex = Optional.ofNullable(nbaUtility.findLastMatch())
                .map(NbaMatch::getGameIdex)
                .orElse(0);

        List<NbaMatch> matchEvent = expandRules(rules, scheduleSeed, lastIndex, limit, season, year);

        return nbaUtility.saveAllMatches(matchEvent);
    }
    private List<MatchRule> buildRules(List<NbaTeam> teams, long seed) {

        List<MatchRule> rules = new ArrayList<>();

        Map<String, List<NbaTeam>> byDivision =
                teams.stream().collect(Collectors.groupingBy(NbaTeam::getDivision));

        // DIVISION (4 games)
        for (List<NbaTeam> div : byDivision.values()) {

            for (int i = 0; i < div.size(); i++) {
                for (int j = i + 1; j < div.size(); j++) {

                    rules.add(new MatchRule(
                            div.get(i).getId(),
                            div.get(j).getId(),
                            4
                    ));
                }
            }
        }

        List<NbaTeam> east = teams.stream()
                .filter(t -> "EAST".equals(t.getConference()))
                .toList();

        List<NbaTeam> west = teams.stream()
                .filter(t -> "WEST".equals(t.getConference()))
                .toList();

        // CONFERENCE (simplified)
        for (List<NbaTeam> conf : List.of(east, west)) {

            List<NbaTeam> shuffled = PrngEngine.shuffle(conf, seed);

            int mid = 6;
            Set<Long> fourGameSet = new HashSet<>();

            for (int i = 0; i < shuffled.size(); i++) {
                for (int j = i + 1; j < shuffled.size(); j++) {

                    int games = (fourGameSet.size() < mid) ? 4 : 3;

                    if (games == 4) {
                        fourGameSet.add(shuffled.get(j).getId());
                    }

                    rules.add(new MatchRule(
                            shuffled.get(i).getId(),
                            shuffled.get(j).getId(),
                            games
                    ));
                }
            }
        }

        // INTER-CONFERENCE (2 games)
        for (NbaTeam e : east) {
            for (NbaTeam w : west) {

                rules.add(new MatchRule(e.getId(), w.getId(), 2));
            }
        }

        return rules;
    }
    private List<NbaMatch> expandRules(
            List<MatchRule> rules,
            long seed,
            int offset,
            int limit,
            Season season,
            Integer year
    ) {

        List<NbaMatch> result = new ArrayList<>();

        int globalIndex = 0;
        int created = 0;

        for (MatchRule rule : rules) {

            for (int i = 0; i < rule.games; i++) {

                // skip until offset
                if (globalIndex < offset + 1) {
                    globalIndex++;
                    continue;
                }

                if (created >= limit) {
                    return result;
                }

                boolean home = (i % 2 == 0);

                long matchSeed = NbaSeedUtil.gameSeed(
                        seed,
                        globalIndex,
                        rule.homeTeamId(),
                        rule.awayTeamId()
                );

                NbaMatch match = new NbaMatch();
                match.setGameIdex(globalIndex);
                match.setHomeTeam(home ? rule.homeTeamId() : rule.awayTeamId());
                match.setAwayTeam(home ? rule.awayTeamId() : rule.homeTeamId());
                match.setSeed(matchSeed);
                match.setEvent(GameEvent.REGULAR);
                match.setGameStatus(GameStatus.UPCOMING);
                match.setSeason(season);
                match.setSeasonYear(year);

                result.add(match);

                globalIndex++;
                created++;
            }
        }

        return result;
    }
}
