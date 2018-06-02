package org.ics4u.tanktrouble.mechanics;

/**
 * bounding circle
 *
 * @author adam
 */
public abstract class BoundingCircle extends BoundingArea {
    /**
     * circle radius
     */
    protected double radius;

    @Override
    public double toEdgeDistance(TankTroubleVector direction) {
        return radius;
    }
}
