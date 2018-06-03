package org.ics4u.tanktrouble.mechanics.obstacles;

import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;
import org.ics4u.tanktrouble.mechanics.powerups.Powerup;
import org.reflections.Reflections;

import java.util.Random;
import java.util.Set;

/**
 * obstacle
 *
 * @author adam
 */
public abstract class Obstacle extends BoundingCircle {
    /**
     * randomly generate subclass instance
     *
     * @return subclass
     */
    public static Obstacle newRandomSubclass() {
        Set<Class<? extends Obstacle>> subclasses = new Reflections("org.ics4u.tanktrouble.mechanics")
                .getSubTypesOf(Obstacle.class);
        int index = new Random().nextInt(subclasses.size());
        int i = 0;
        for (Class<? extends Obstacle> subclass : subclasses) {
            if (i==index) {
                try {
                    return subclass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return null;
    }
}
