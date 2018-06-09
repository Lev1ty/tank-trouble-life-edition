import java.util.Random;

public class Utility {
    public static final Random random = new Random();

    public static int randomRange(int lo, int hi) {
        return random.nextInt(hi - lo + 1) + lo;
    }
}
