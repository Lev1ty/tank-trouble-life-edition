package org.ics4u.tanktrouble.buckets;

import org.ics4u.tanktrouble.mechanics.powerups.Powerup;

/**
 * powerups container
 *
 * @author adam
 */
public class Powerups extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Powerups(int powerups) {
        for (int i = 0; i < powerups; i++)
            add();
    }

    @Override
    public void add() {
        objects.add(Powerup.newRandomSubclass());
    }
}
