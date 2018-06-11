/**
 * bush
 *
 * @author adam
 */
public class Bush extends ObjectBuilder {
    @Override
    public void act() {
        // do nothing
    }

    @Override
    public void actOn(Object other) {
        // bounce any moving object
        if ((other instanceof DynamicObject) && edgeToEdgeDistance(other) <= 0) {
            ((DynamicObject) other).reverse();
        }
    }
}
