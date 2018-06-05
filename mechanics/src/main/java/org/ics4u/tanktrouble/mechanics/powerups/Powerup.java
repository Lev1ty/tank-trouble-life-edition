package org.ics4u.tanktrouble.mechanics.powerups;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;
import org.ics4u.tanktrouble.utilities.Utilities;
import org.reflections.Reflections;

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
    public Powerup(Pane pane) {
        super(pane, 15, Color.AQUA);
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
    public static Powerup newRandomSubclass(Pane pane) {
        Set<Class<? extends Powerup>> subclasses = new Reflections("org.ics4u.tanktrouble.mechanics.powerup")
                .getSubTypesOf(Powerup.class);
        int index = Utilities.rng.nextInt(subclasses.size());
        int i = 0;
        for (Class<? extends Powerup> subclass : subclasses) {
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
