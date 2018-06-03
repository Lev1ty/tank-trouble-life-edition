package org.ics4u.tanktrouble.buckets;

import org.ics4u.tanktrouble.mechanics.tanks.AITank;
import org.ics4u.tanktrouble.mechanics.tanks.PlayerTank;

/**
 * tanks container
 *
 * @author adam
 */
public class Tanks extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Tanks(int players, int ais) {
        super();
        if (players > 3) log.error("too many players (more than 3)");
        for (int i = 0; i < players; i++)
            objects.add(new PlayerTank());
        if (ais > 10) log.error("too many AIs (more than 10)");
        for (int i = 0; i < ais; i++)
            add();
    }

    @Override
    public void add() {
        objects.add(new AITank());
    }
}
