/**
 * tank class
 *
 * @author adam
 */
public class Tank extends Friendable {
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
     * bullet counter
     */
    public int bullets;

    /**
     * randomized-value constructor
     *
     * @param imagePath path to avatar image of tank
     */
    public Tank(String imagePath) {
        super(imagePath, STEP);
        // zero score
        kills = 0;
        bullets = 0;
    }

    /**
     * check if blocked
     *
     * @param other object to be checked
     * @return whether this is blocked by object
     */
    public boolean block(Object other) {
        return !(other instanceof Bullet) && id != other.id && hit(other);
    }

    /**
     * to-do when blocked
     */
    public void blocked() {
        // only allow one button to be effective to prevent black hole effect
        // north and south take priority
        if (north & east) east = false;
        if (north & west) west = false;
        if (south & east) east = false;
        if (south & west) west = false;
        // counteract headings to simulate no movement
        if (north) goSouth();
        if (south) goNorth();
        if (east) goWest();
        if (west) goEast();
    }
}
