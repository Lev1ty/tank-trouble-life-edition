import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * moving object
 *
 * @author adam
 */
public abstract class DynamicObject extends Object {
    /**
     * speed of movement per frame
     */
    public double step;
    /**
     * rotation mechanism
     */
    public Rotate rotate;
    /**
     * translation mechanism
     */
    public Translate translate;
    /**
     * current headings
     */
    public boolean north, south, east, west;

    /**
     * randomly-initialized contructor
     *
     * @param imagePath path to avatar image
     * @param step      movement per frame
     */
    public DynamicObject(String imagePath, double step) {
        // put default (x=0, y=0, angle=0) into super first
        this(imagePath, 0, 0, 0, step);
        // set randomized initial position
        // receive center position
        int[] position = generateRandomNonoverlappingPosition();
        // adjust to canvas location (top left point of avatar image)
        translate.setX(position[0] - imageView.getImage().getWidth() / 2);
        translate.setY(position[1] - imageView.getImage().getHeight() / 2);
    }

    /**
     * value constructor
     *
     * @param imagePath path to avatar image
     * @param x         x-coordinate position
     * @param y         y-coordinate position
     * @param step      movement per frame
     */
    public DynamicObject(String imagePath, double x, double y, double angle, double step) {
        super(imagePath);
        // initialize mechanisms
        rotate = new Rotate();
        translate = new Translate();
        // set initial position adjusted to top left point of avatar image
        translate.setX(x - imageView.getImage().getWidth() / 2);
        translate.setY(y - imageView.getImage().getHeight() / 2);
        // set rotation pivot to center of
        rotate.setPivotX(translate.getX() + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(translate.getY() + imageView.getImage().getHeight() / 2);
        // add transform mechanisms to avatar image
        imageView.getTransforms().addAll(rotate, translate);
        // rotate according to angle
        rotate.setAngle(angle);
        // set step
        this.step = step;
    }

    /**
     * copy constructor with custom position
     *
     * @param other     object to copy from
     * @param x         custom x-coordinate position
     * @param y         custom y-coordinate position
     * @param imagePath custom image path
     * @param step      custom step
     */
    public DynamicObject(DynamicObject other, double x, double y, String imagePath, double step) {
        this(imagePath, x, y, other.rotate.getAngle(), step);
    }

    /**
     * utility method to prepare for frame
     */
    public void initializeForFrame() {
        updateRotatePivot();
    }

    /**
     * update pivot point according to canvas position
     */
    public void updateRotatePivot() {
        // set pivot at center of avatar image
        rotate.setPivotX(translate.getX() + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(translate.getY() + imageView.getImage().getHeight() / 2);
    }

    /**
     * check for position outside field
     *
     * @return if position is outside field
     */
    public boolean outOfBounds() {
        return translate.getX() < 0 || translate.getX() > WIDTH - imageView.getImage().getWidth() + SPILL
                || translate.getY() < 0 || translate.getY() > HEIGHT - imageView.getImage().getHeight() + SPILL;
    }

    /**
     * override logic and go in reverse direction
     */
    public void reverseNow() {
        if (north) goSouth();
        if (south) goNorth();
        if (east) goWest();
        if (west) goEast();
    }

    /**
     * head north
     */
    public void goNorth() {
        translate.setX(translate.getX() + step * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() + step * Math.sin(rotate.getAngle() * Math.PI / 180));
        if (outOfBounds()) reverseNow();
    }

    /**
     * head south
     */
    public void goSouth() {
        translate.setX(translate.getX() - step * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() - step * Math.sin(rotate.getAngle() * Math.PI / 180));
        if (outOfBounds()) reverseNow();
    }

    /**
     * rotate clockwise
     */
    public void goEast() {
        rotate.setAngle(rotate.getAngle() + step);
        if (outOfBounds()) reverseNow();
    }

    /**
     * rotate counterclockwise
     */
    public void goWest() {
        rotate.setAngle(rotate.getAngle() - step);
        if (outOfBounds()) reverseNow();
    }
}
