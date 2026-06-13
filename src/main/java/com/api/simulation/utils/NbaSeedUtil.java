package com.api.simulation.utils;

import com.api.simulation.constants.nba.Season;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.StringJoiner;

public class NbaSeedUtil {

    private static final String NBA = "NBA";
    private static final String SEASON = "SEASON";
    private static final String SCHEDULE = "SCHEDULE";
    private static final String GAME = "GAME";
    private static final String POSSESSION = "POSSESSION";

    // base seed
    public static long seasonSeed(Season season, int year) {
        return hash(build( NBA, SEASON, season.name(), year));
    }

    public static long scheduleSeed(long seasonSeed) {
        return hash(build(NBA, SCHEDULE, seasonSeed));
    }

    public static long gameSeed(long seasonSeed, Integer gameIndex, Long homeTeam, Long awayTeam) {
        return hash(build(NBA, GAME, seasonSeed, gameIndex, homeTeam, awayTeam));
    }

    public static long possessionSeed(long gameSeed, int possessionIndex) {
        return hash(build(NBA, POSSESSION, gameSeed, possessionIndex));
    }

    private static String build(Object... parts) {
        StringJoiner joiner = new StringJoiner(":");

        for (Object part : parts) {
            if (part != null) {
                joiner.add(String.valueOf(part));
            }
        }

        return joiner.toString();
    }

    private static long hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            long value = 0;
            for (int i = 0; i < 8; i++) {
                value = (value << 8) | (digest[i] & 0xff);
            }
            return Math.abs(value);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate seed", e);
        }
    }

}
