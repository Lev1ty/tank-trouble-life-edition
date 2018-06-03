package org.ics4u.tanktrouble.mechanics.bullets;

import org.ics4u.tanktrouble.mechanics.meta.BoundingCircle;

/**
 * bullet
 *
 * @author adam
 */
public abstract class Bullet extends BoundingCircle {
    /**
     * randomly generate subclass instance
     *
     * @return subclass
     */
    public static Bullet newRandomSubclass() {
//        switch (new Random().nextInt(0)) {
        switch (0) {
            case 0:
                return new Standard();
        }
        return null;
    }
}
