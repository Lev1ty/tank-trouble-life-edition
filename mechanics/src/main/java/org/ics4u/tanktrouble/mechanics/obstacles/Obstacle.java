package org.ics4u.tanktrouble.mechanics.obstacles;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;
import org.ics4u.tanktrouble.utilities.Utilities;
import org.reflections.Reflections;

import java.util.Set;

/**
 * obstacle
 *
 * @author adam
 */
public abstract class Obstacle extends BoundingCircle {
    /**
     * total instances of Obstacle
     */
    private static int count = 0;
    /**
     * index of this Obstacle
     */
    public int id;

    /**
     * default constructor
     */
    public Obstacle(Pane pane) {
        super(pane, Utilities.randomRange(40, 100), Color.RED);
        id = count;
        log.info("Obstacle id: " + id);
        count++;
    }

    /**
     * reset count
     */
    public static void resetCount() {
        count = 0;
    }

    /**
     * randomly generate subclass instance
     *
     * @return subclass
     */
    public static Obstacle newRandomSubclass(Pane pane) {
        Set<Class<? extends Obstacle>> subclasses = new Reflections("org.ics4u.tanktrouble.mechanics")
                .getSubTypesOf(Obstacle.class);
        int index = Utilities.rng.nextInt(subclasses.size());
        int i = 0;
        for (Class<? extends Obstacle> subclass : subclasses) {
            if (i == index) {
                try {
                    return subclass.getDeclaredConstructor(Pane.class).newInstance(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return null;
    }
}
