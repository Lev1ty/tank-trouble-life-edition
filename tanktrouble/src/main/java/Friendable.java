import java.util.HashSet;
import java.util.Set;

/**
 * friendable layer
 * <p>
 * pipes all constructors to super while initializing friendlies
 *
 * @author adam
 */
public abstract class Friendable extends DynamicObject {
    /**
     * friendly units
     * <p>
     * bullets don't kill friendlies
     */
    public Set<Integer> friendlies;

    /**
     * randomly-initialized constructor
     *
     * @param imagePath path to avatar image
     * @param step      movement per frame
     */
    public Friendable(String imagePath, double step) {
        super(imagePath, step);
        initializeFriendlies();
    }

    /**
     * value constructor
     *
     * @param imagePath path to avatar image
     * @param x         x-coordinate position
     * @param y         y-coordinate position
     * @param angle     angle
     * @param step      movement per frame
     */
    public Friendable(String imagePath, double x, double y, double angle, double step) {
        super(imagePath, x, y, angle, step);
        initializeFriendlies();
    }

    /**
     * copy constructor with custom position
     *
     * @param other
     * @param x
     * @param y
     * @param imagePath
     * @param step
     */
    public Friendable(DynamicObject other, double x, double y, String imagePath, double step) {
        super(imagePath, x, y, other.rotate.getAngle(), step);
        initializeFriendlies();
    }

    /**
     * initialize friendlies
     */
    public void initializeFriendlies() {
        // initialize friendlies
        friendlies = new HashSet<>();
        // become friends with self
        becomeFriends(this);
    }

    /**
     * become friends with other object
     *
     * @param other object to be friended
     */
    public void becomeFriends(Friendable other) {
        // add other to own set of friends
        friendlies.add(other.id);
        // add this to other set of friends
        other.friendlies.add(id);
    }

    /**
     * check friendly
     *
     * @param other object to check
     * @return if object is friendly
     */
    public boolean isFriend(Object other) {
        // check own set of friendlies
        for (Integer integer : friendlies)
            if (other.id == integer)
                return true;
        return false;
    }
}
