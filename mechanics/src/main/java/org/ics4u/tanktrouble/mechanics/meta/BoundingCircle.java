package org.ics4u.tanktrouble.mechanics.meta;

import org.ics4u.tanktrouble.mechanics.meta.vectors.TankTroubleVector;

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
