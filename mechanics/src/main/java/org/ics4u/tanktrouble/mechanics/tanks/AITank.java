package org.ics4u.tanktrouble.mechanics.tanks;

import javafx.scene.layout.Pane;

/**
 * AI controlled tank
 *
 * @author adam
 */
public class AITank extends Tank {
    /**
     * total instances of AITank
     */
    private static int count = 0;
    /**
     * index of this AI
     */
    public int id;

    /**
     * default constructor
     */
    public AITank(Pane pane) {
        super(pane);
        id = count;
        log.info("AITank id: " + id);
        count++;
    }

    /**
     * reset count
     */
    public static void resetCount() {
        count = 0;
    }
}
