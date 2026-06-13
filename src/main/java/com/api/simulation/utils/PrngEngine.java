package com.api.simulation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class PrngEngine {

    private final Random random;

    public PrngEngine(long seed) {
        random = new Random(seed);
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public boolean chance(double probability) {
        return random.nextDouble() < probability;
    }

    public static <T> List<T> shuffle(List<T> list, long seed) {

        List<T> copy = new ArrayList<>(list);

        Random prng = new Random(seed); // 👈 PRNG seeded

        Collections.shuffle(copy, prng);

        return copy;
    }
}
