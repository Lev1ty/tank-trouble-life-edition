public class Bullet extends DynamicObject {
    public static final double STEP = 3;
    public int ownerId;

    public Bullet(DynamicObject other, String imagePath) {
        super(other, imagePath, STEP);
        translate.setX(translate.getX() + other.imageView.getImage().getWidth() / 2 - imageView.getImage().getWidth() / 2);
        translate.setY(translate.getY() + other.imageView.getImage().getHeight() / 2 - imageView.getImage().getHeight() / 2);
        becomeFriends(other);
        ownerId = other.id;
    }
}
