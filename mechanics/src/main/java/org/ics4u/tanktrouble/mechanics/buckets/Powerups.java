package org.ics4u.tanktrouble.mechanics.buckets;

import javafx.scene.layout.Pane;
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
    public Powerups(Pane pane, int number) {
        super(pane, number);
    }

    @Override
    public void add(Pane pane) {
        super.add(pane);
        objects.add(Powerup.newRandomSubclass(pane));
    }
}
