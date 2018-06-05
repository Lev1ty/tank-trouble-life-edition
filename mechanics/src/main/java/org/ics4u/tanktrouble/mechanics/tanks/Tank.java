package org.ics4u.tanktrouble.mechanics.tanks;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;

/**
 * tank
 *
 * @author adam
 */
public abstract class Tank extends BoundingCircle {
    /**
     * total instances of Tank
     */
    private static int count = 0;
    /**
     * index of this tank
     */
    public int id;

    /**
     * default constructor
     */
    public Tank(Pane pane) {
        super(pane, 25, Color.GREEN);
        id = count;
        log.info("Tank id: " + id);
        count++;
    }

    /**
     * reset count
     */
    public static void resetCount() {
        count = 0;
    }
}
