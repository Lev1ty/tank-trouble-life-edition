package org.ics4u.tanktrouble.mechanics;

import java.util.Random;

/**
 * orientation vector (rotation vector)
 *
 * @author adam
 * @implNote orientation vector is always a unit vector
 */
public class OrientationVector extends TankTroubleVector {
    /**
     * default constructor initializes to random direction
     */
    public OrientationVector() {
        super();
        randomize();
    }

    /**
     * value constructor
     *
     * @param x x-component
     * @param y y-component
     */
    public OrientationVector(double x, double y) {
        super(x, y);
        normalize();
    }

    /**
     * copy constructor
     *
     * @param other template
     */
    public OrientationVector(TankTroubleVector other) {
        this(other.x, other.y);
    }

    /**
     * normalize components
     */
    private void normalize() {
        double sum = Math.abs(x) + Math.abs(y);
        x /= sum;
        y /= sum;
    }

    /**
     * randomize orientation
     */
    private void randomize() {
        Random rng = new Random();
        x = (rng.nextInt() == 0 ? -1 : 1) * rng.nextDouble();
        y = (rng.nextInt() == 0 ? -1 : 1) * (1 - Math.abs(x));
    }
}
