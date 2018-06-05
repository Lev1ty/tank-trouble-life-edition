package org.ics4u.tanktrouble.mechanics.buckets;

import javafx.scene.layout.Pane;
import org.ics4u.tanktrouble.mechanics.bullets.Bullet;

/**
 * bullets container
 *
 * @author adam
 */
public class Bullets extends TankTroubleBucket {
    /**
     * value constructor
     */
    public Bullets(Pane pane, int number) {
        super(pane, number);
    }

    @Override
    public void add(Pane pane) {
        super.add(pane);
        objects.add(Bullet.newRandomSubclass(pane));
    }
}
