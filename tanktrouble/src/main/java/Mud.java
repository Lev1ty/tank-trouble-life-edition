/**
 * mud
 *
 * @author adam
 */
public class Mud extends ObjectBuilder {
    @Override
    public void act() {
        // do nothing
    }

    @Override
    public void actOn(Object other) {
        // slow any moving object
        if (other instanceof DynamicObject && edgeToEdgeDistance(other) <= 0) {
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
