package org.ics4u.tanktrouble.mechanics.bullets;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;
import org.ics4u.tanktrouble.utilities.Utilities;
import org.reflections.Reflections;

import java.util.Set;

/**
 * bullet
 *
 * @author adam
 */
public abstract class Bullet extends BoundingCircle {
    /**
     * total instances of Bullet
     */
    private static int count = 0;
    /**
     * index of this Bullet
     */
    public int id;

    /**
     * default constructor
     */
    public Bullet(Pane pane) {
        super(pane, 5, Color.BLACK);
        id = count;
        log.info("Bullet id: " + id);
        count++;
    }

    /**
     * randomly generate subclass instance
     *
     * @return subclass
     */
    public static Bullet newRandomSubclass(Pane pane) {
        Set<Class<? extends Bullet>> subclasses = new Reflections("org.ics4u.tanktrouble.mechanics")
                .getSubTypesOf(Bullet.class);
        int index = Utilities.rng.nextInt(subclasses.size());
        int i = 0;
        for (Class<? extends Bullet> subclass : subclasses) {
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
