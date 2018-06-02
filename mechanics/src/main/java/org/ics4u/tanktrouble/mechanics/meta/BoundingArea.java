package org.ics4u.tanktrouble.mechanics.meta;

import org.ics4u.tanktrouble.mechanics.meta.vectors.*;

/**
 * bounding area
 *
 * @author adam
 */
public abstract class BoundingArea extends TankTroubleObject {
    /**
     * position vector
     */
    protected PositionVector position;
    /**
     * orientation vector
     */
    protected OrientationVector orientation;
    /**
     * movement vector
     */
    protected MovementVector movement;
    /**
     * acceleration vector
     */
    protected AccelerationVector acceleration;

    /**
     * center-to-center distance
     * @param other reference
     * @return distance
     */
    public double centerCenterDistance(BoundingArea other) {
        return (other.position.subtract(position)).scalar();
    }

    /**
     * center-to-edge distance
     *
     * @param direction of point on edge
     * @return distance
     */
    public abstract double toEdgeDistance(TankTroubleVector direction);

    /**
     * edge-to-edge distance
     *
     * @param other reference
     * @return distance
     */
    public double edgeEdgeDistance(BoundingArea other) {
        return centerCenterDistance(other)
                - toEdgeDistance(other.position.subtract(position))
                - other.toEdgeDistance(position.subtract(other.position));
    }
}
