/**
 * tank class
 *
 * @author adam
 */
public class Tank extends DynamicObject {
    /**
     * movement per frame
     */
    public static final double STEP = 2;
    /**
     * fire state
     */
    public boolean fire;
    /**
     * score counter
     */
    public int kills;

    /**
     * randomized-value constructor
     *
     * @param imagePath path to avatar image of tank
     */
    public Tank(String imagePath) {
        super(imagePath, STEP);
        // zero score
        kills = 0;
    }

    /**
     * value constructor
     *
     * @param imagePath path to avatar image of tank
     * @param x x-coordinate position
     * @param y y-coordinate position
     */
    public Tank(String imagePath, double x, double y) {
        super(imagePath, x, y, 0, STEP);
    }
}
