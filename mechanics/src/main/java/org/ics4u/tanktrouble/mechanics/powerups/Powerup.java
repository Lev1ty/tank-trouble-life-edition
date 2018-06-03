package org.ics4u.tanktrouble.mechanics.powerups;

import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;
import org.reflections.Reflections;

import java.util.Random;
import java.util.Set;

public abstract class Powerup extends BoundingCircle {
    /**
     * total instances of Powerup
     */
    private static int count = 0;
    /**
     * index of this Powerup
     */
    public int id;

    /**
     * default constructor
     */
    public Powerup() {
        id = count;
        log.info("Powerup id: " + id);
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
    public static Powerup newRandomSubclass() {
        Set<Class<? extends Powerup>> subclasses = new Reflections("org.ics4u.tanktrouble.mechanics")
                .getSubTypesOf(Powerup.class);
        int index = new Random().nextInt(subclasses.size());
        int i = 0;
        for (Class<? extends Powerup> subclass : subclasses) {
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
