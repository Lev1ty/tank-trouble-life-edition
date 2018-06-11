/**
 * dynamic object
 *
 * @author adam
 */
public abstract class DynamicObject extends ObjectBuilder {
    /**
     * heading
     */
    public boolean north, south, east, west;
    /**
     * movement multiplier
     */
    public double movementMultiplier;

    /**
     * default constructor
     */
    public DynamicObject() {
        super();
        movementMultiplier = 1;
    }

    @Override
    public void act() {
        // update rotate pivot to current center
        updateRotatePivot();
        // act on heading
        if (north) {
            goNorth();
        }
        if (south) {
            goSouth();
        }
        if (east) {
            goEast();
        }
        if (west) {
            goWest();
        }
        // contain within window
        if (outOfBounds()) {
            reverse();
        }
    }

    /**
     * reverse
     * <p>
     * can bounce or be stuck
     */
    protected abstract void reverse();

    /**
     * update rotate pivot
     */
    private void updateRotatePivot() {
        rotate.setPivotX(translate.getX() + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(translate.getY() + imageView.getImage().getHeight() / 2);
    }

    /**
     * out of bounds
     */
    private boolean outOfBounds() {
        return translate.getX() < 0 || translate.getX() > WIDTH - imageView.getImage().getWidth()
                || translate.getY() < 0 || translate.getY() > HEIGHT - imageView.getImage().getHeight();
    }

    /**
     * go toward top of screen
     */
    protected abstract void goNorth();

    /**
     * go toward bottom of screen
     */
    protected abstract void goSouth();

    /**
     * rotate clockwise
     */
    protected abstract void goEast();

    /**
     * rotate counterclockwise
     */
    protected abstract void goWest();
}
