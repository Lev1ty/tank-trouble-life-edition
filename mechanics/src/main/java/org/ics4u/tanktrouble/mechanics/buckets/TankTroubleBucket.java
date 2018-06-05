package org.ics4u.tanktrouble.mechanics.buckets;

import javafx.scene.layout.Pane;
import org.ics4u.tanktrouble.mechanics.meta.TankTroubleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class TankTroubleBucket {
    /**
     * game objects container
     */
    public static List<TankTroubleObject> objects = new ArrayList<>();
    /**
     * global logger
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * default constructor
     */
    protected TankTroubleBucket() {
        log.info("bucket object construction successful");
    }

    /**
     * value constructor
     */
    protected TankTroubleBucket(Pane pane, int number) {
        this();
        for (int i = 0; i < number; i++)
            add(pane);
    }

    /**
     * re-initialize objects
     */
    public static void reset() {
        objects = new ArrayList<>();
    }

    /**
     * add object
     */
    public void add(Pane pane) {
        log.info("object added");
    }

    /**
     * remove object
     */
    public void remove(TankTroubleObject object) {
        for (int i = 0; i < objects.size(); i++)
            if (objects.get(i) == object)
                objects.remove(i);
    }
}
