package org.ics4u.tanktrouble.mechanics.meta.vectors;

import org.ics4u.tanktrouble.mechanics.meta.TankTroubleObject;

/**
 * general vector class
 *
 * @author adam
 * @implNote information stored in cartesian form
 */
public class TankTroubleVector extends TankTroubleObject {
    /**
     * x-component
     */
    protected double x;
    /**
     * y-component
     */
    protected double y;

    /**
     * default constructor
     */
    public TankTroubleVector() {
        this(0, 0);
    }

    /**
     * value constructor
     *
     * @param x x-component
     * @param y y-component
     */
    public TankTroubleVector(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * copy constructor
     *
     * @param other template
     */
    public TankTroubleVector(TankTroubleVector other) {
        this(other.x, other.y);
    }

    /**
     * scalar conversion
     *
     * @return magnitude
     */
    public double scalar() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * vector negation
     *
     * @return negative vector
     */
    public TankTroubleVector negate() {
        return new TankTroubleVector(-this.x, -this.y);
    }

    /**
     * vector addition
     *
     * @param other operand
     * @return sum
     */
    public TankTroubleVector add(TankTroubleVector other) {
        return new TankTroubleVector(this.x + other.x, this.y + other.y);
    }

    /**
     * vector subtraction
     *
     * @param other operand
     * @return difference
     */
    public TankTroubleVector subtract(TankTroubleVector other) {
        return add(other.negate());
    }

    /**
     * vector dot (inner) product
     *
     * @param other operand
     * @return scalar dot product
     */
    public double dot(TankTroubleVector other) {
        return this.x * other.x + this.y * other.y;
    }
}
