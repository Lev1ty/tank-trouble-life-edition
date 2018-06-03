package org.ics4u.tanktrouble.buckets;

import org.ics4u.tanktrouble.mechanics.meta.TankTroubleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class TankTroubleBucket {
    /**
     * global logger
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * game objects container
     */
    public static List<TankTroubleObject> objects = new ArrayList<>();

    /**
     * default constructor
     */
    protected TankTroubleBucket() {
        log.info("bucket object construction successful");
    }

    /**
     * value constructor
     */
    protected TankTroubleBucket(int number) {
        this();
        for (int i = 0; i < number; i++)
            add();
    }

    /**
     * add object
     */
    public abstract void add();

    /**
     * remove object
     */
    public void remove(TankTroubleObject object) {
        for (int i = 0; i < objects.size(); i++)
            if (objects.get(i) == object)
                objects.remove(i);
    }

    /**
     * re-initialize objects
     */
    public static void reset() {
        objects = new ArrayList<>();
    }

    public static void main(String[] args) {
        TankTroubleBucket tanks = new Tanks(3, 10);
        TankTroubleBucket powerups = new Powerups(50);
        TankTroubleBucket obstacles = new Obstacles(20);
        TankTroubleBucket bullets = new Bullets(20);
        System.out.println(objects.size());
        TankTroubleObject object = objects.get(0);
        objects.remove(object);
        System.out.println(objects.size());
    }
}
