/**
 * bullet class
 *
 * @author adam
 */
public class Bullet extends DynamicObject {
    /**
     * movement per frame
     */
    public static final double STEP = 3;
    /**
     * tank that fired this bullet
     */
    public int ownerId;

    /**
     * copy constructor
     *
     * @param other usually the tank that fired it
     * @param imagePath bullet image
     */
    public Bullet(DynamicObject other, String imagePath) {
        super(other, other.translate.getX() + other.imageView.getImage().getWidth() / 2,
                other.translate.getY() + other.imageView.getImage().getHeight() / 2, imagePath, STEP);
        // be friends with tank that fired this
        becomeFriends(other);
        // set the tank as owner
        ownerId = other.id;
    }
}
