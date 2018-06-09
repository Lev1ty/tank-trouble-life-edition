import java.util.Random;

/**
 * utilities
 *
 * @author adam
 */
public class Utility {
    /**
     * rng
     */
    public static final Random random = new Random();

    /**
     * get random integer within range
     *
     * @param lo lower bound
     * @param hi higher bound
     * @return random integer within inclusive range
     */
    public static int randomRange(int lo, int hi) {
        return random.nextInt(hi - lo + 1) + lo;
    }
}
