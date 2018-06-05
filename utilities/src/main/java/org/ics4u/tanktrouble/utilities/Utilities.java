package org.ics4u.tanktrouble.utilities;

import java.util.Random;

public abstract class Utilities {
    /**
     * rng
     */
    public static final Random rng = new Random();

    /**
     * generate random integer within range
     *
     * @param lo lower bound
     * @param hi upper bound
     * @return random integer within range
     */
    public static int randomRange(int lo, int hi) {
        return rng.nextInt(hi - lo + 1) + lo;
    }
}
