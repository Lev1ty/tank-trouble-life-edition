package org.ics4u.tanktrouble.mechanics.buckets;

import javafx.scene.layout.Pane;
import org.ics4u.tanktrouble.mechanics.tanks.AITank;
import org.ics4u.tanktrouble.mechanics.tanks.Player1;
import org.ics4u.tanktrouble.mechanics.tanks.Player2;
import org.ics4u.tanktrouble.mechanics.tanks.Player3;

/**
 * tanks container
 *
 * @author adam
 */
public class Tanks extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Tanks(Pane pane, int players, int ais) {
        super();
        if (players > 3) log.error("too many players (more than 3)");
        objects.add(new Player1(pane));
        objects.add(new Player2(pane));
        objects.add(new Player3(pane));
        if (ais > 10) log.error("too many AIs (more than 10)");
        for (int i = 0; i < ais; i++)
            add(pane);
    }

    @Override
    public void add(Pane pane) {
        super.add(pane);
        objects.add(new AITank(pane));
    }
}
