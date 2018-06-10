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
     * update rotate pivot
     */
    protected void updateRotatePivot() {
        this.rotate.setPivotX(this.translate.getX() + this.imageView.getImage().getWidth() / 2);
        this.rotate.setPivotY(this.translate.getY() + this.imageView.getImage().getHeight() / 2);
    }

    /**
     * out of bounds
     */
    protected boolean outOfBounds() {
        return translate.getX() < 0 || translate.getX() > WIDTH - imageView.getImage().getWidth()
                || translate.getY() < 0 || translate.getY() > HEIGHT - imageView.getImage().getHeight();
    }

    protected abstract void goNorth();

    protected abstract void goSouth();

    protected abstract void goEast();

    protected abstract void goWest();
}
