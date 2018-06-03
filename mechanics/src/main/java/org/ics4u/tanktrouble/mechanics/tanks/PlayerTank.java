package org.ics4u.tanktrouble.mechanics.tanks;

/**
 * player controlled tank
 *
 * @author adam
 */
public class PlayerTank extends Tank {
    /**
     * total instances of PlayerTank
     */
    private static int count = 0;
    /**
     * index of this Player
     */
    public int id;

    /**
     * default constructor
     */
    public PlayerTank() {
        super();
        id = count;
        log.info("PlayerTank id: " + id);
        count++;
    }

    /**
     * reset count
     */
    public static void resetCount() {
        count = 0;
    }
}
