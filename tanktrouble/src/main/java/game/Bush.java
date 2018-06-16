package game;

/**
 * bush
 */
public class Bush extends ObjectBuilder {
    @Override
    public void act() {
        // do nothing
    }

    @Override
    public void actOn(Object other) {
        if (edgeToEdgeDistance(other) <= 0) {
            // reverse tank object
            if (other instanceof Tank) {
                ((DynamicObject) other).reverse();
            }
            // slow bullet object
            if (other instanceof Bullet) {
                // apply slowing
                ((DynamicObject) other).movementMultiplier = SLOW_MULTIPLIER;
                // counteract heading
                if (((DynamicObject) other).north) {
                    ((DynamicObject) other).goSouth();
                }
                if (((DynamicObject) other).south) {
                    ((DynamicObject) other).goNorth();
                }
                if (((DynamicObject) other).east) {
                    ((DynamicObject) other).goWest();
                }
                if (((DynamicObject) other).west) {
                    ((DynamicObject) other).goEast();
                }
                // remove slowing
                ((DynamicObject) other).movementMultiplier = 1;
            }
        }
    }
}
