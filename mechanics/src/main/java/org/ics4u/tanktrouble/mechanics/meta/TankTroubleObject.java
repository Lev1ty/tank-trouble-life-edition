package org.ics4u.tanktrouble.mechanics.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * metaclass for tanktrouble
 *
 * @author adam
 */
public abstract class TankTroubleObject {
    /**
     * global logger
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * default constructor
     */
    public TankTroubleObject() {
        log.info("construction successful");
    }
}
