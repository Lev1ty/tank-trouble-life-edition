import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

/**
 * game object
 *
 * @author adam
 */
public abstract class Object implements ApplicationParameters {
    /**
     * total number of objects on the field
     */
    public static int idCount = 0;
    /**
     * object index of this
     */
    public int id;
    /**
     * path to avatar
     */
    public String imagePath;
    /**
     * avatar image container
     */
    public ImageView imageView;
    /**
     * friendly units
     * <p>
     * bullets don't kill friendlies
     */
    public Set<Integer> friendlies;

    /**
     * default constructor
     *
     * @param imagePath path to avatar image
     */
    public Object(String imagePath) {
        // set count to current total
        id = idCount;
        // update total count
        idCount++;
        // store path to image
        this.imagePath = imagePath;
        // initialize avatar
        imageView = new ImageView(new Image(imagePath));
        // initialize friendlies
        friendlies = new HashSet<>();
        // become friends with self
        becomeFriends(this);
    }

    /**
     * edge-to-edge distance to another object
     *
     * @param other object to be probed
     * @param x     arbitrary x-coordinate of this
     * @param y     arbitrary y-coordinate of this
     * @return distance between arbitrary position and object
     */
    public double distanceTo(Object other, int x, int y) {
        // magnitude between centers - radius of other - radius of this
        return Math.sqrt(Math.pow(((DynamicObject) other).translate.getX() + other.imageView.getImage().getWidth() / 2 - x, 2) +
                Math.pow(((DynamicObject) other).translate.getY() + other.imageView.getImage().getHeight() / 2 - y, 2)) -
                other.imageView.getImage().getWidth() / 2 - imageView.getImage().getWidth() / 2;
    }

    /**
     * edge-to-edge distance to another object
     *
     * @param other object to be probed
     * @return distance between current position and object
     */
    public double distanceTo(Object other) {
        return distanceTo(other, (int) (((DynamicObject) this).translate.getX() + imageView.getImage().getWidth() / 2),
                (int) (((DynamicObject) this).translate.getY() + imageView.getImage().getHeight() / 2));
    }

    /**
     * check to kill other object
     *
     * @param other object to check
     * @return kill object or not
     */
    public boolean kill(Object other) {
        // check if hit
        if (!hit(other)) return false;
        // check own set of friendlies
        for (Integer integer : friendlies)
            if (other.id == integer)
                return false;
        // intentional fallthrough
        return true;
    }

    /**
     * check if hit other
     *
     * @param other object to check
     * @return hit or not
     */
    public boolean hit(Object other) {
        // edge-to-edge distance is non-positive
        return distanceTo(other) <= 0;
    }

    /**
     * become friends with other object
     *
     * @param other object to be friended
     */
    public void becomeFriends(Object other) {
        // add other to own set of friends
        friendlies.add(other.id);
        // add this to other set of friends
        other.friendlies.add(id);
    }

    /**
     * generate random nonoverlapping position on field
     *
     * @return {x, y} <- center of nonoverlapping position
     */
    public int[] generateRandomNonoverlappingPosition() {
        // initial random positions generated within field
        int x = Utility.randomRange((int) imageView.getImage().getWidth() / 2,
                WIDTH - (int) imageView.getImage().getWidth() / 2);
        int y = Utility.randomRange((int) imageView.getImage().getHeight() / 2,
                HEIGHT - (int) imageView.getImage().getHeight() / 2);
        // continuously generate positions until no overlap
        while (true) {
            // no overlap OK!
            boolean flag = true;
            // check over global array
            for (Object object : Bucket.objects) {
                // hypothetical position overlaps
                if (distanceTo(object, x, y) < 0) {
                    // regenerate positions
                    x = Utility.randomRange((int) imageView.getImage().getWidth() / 2,
                            WIDTH - (int) imageView.getImage().getWidth() / 2);
                    y = Utility.randomRange((int) imageView.getImage().getHeight() / 2,
                            HEIGHT - (int) imageView.getImage().getHeight() / 2);
                    // signal overlap
                    flag = false;
                    break;
                }
            }
            // stop loop once nonoverlapping position is found
            if (flag) break;
        }
        // return position of center of avatar image
        return new int[]{x, y};
    }
}
