package org.ics4u.tanktrouble.mechanics.buckets;

import javafx.scene.layout.Pane;
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
    public Obstacles(Pane pane, int number) {
        super(pane, number);
    }

    @Override
    public void add(Pane pane) {
        super.add(pane);
        objects.add(Obstacle.newRandomSubclass(pane));
    }
}
