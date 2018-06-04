package org.ics4u.tanktrouble.buckets;

import org.ics4u.tanktrouble.mechanics.bullets.Standard;

/**
 * bullets container
 *
 * @author adam
 */
public class Bullets extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Bullets(int number) {
        super(number);
    }

    @Override
    public void add() {
        objects.add(new Standard());
    }
}
