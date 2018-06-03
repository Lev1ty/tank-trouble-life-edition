package org.ics4u.tanktrouble.buckets;

import org.ics4u.tanktrouble.mechanics.obstacles.Obstacle;

/**
 * obstacles container
 *
 * @author adam
 */
public class Obstacles extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Obstacles(int obstacles) {
        for (int i = 0; i < obstacles; i++)
            add();
    }

    @Override
    public void add() {
        objects.add(Obstacle.newRandomSubclass());
    }
}
