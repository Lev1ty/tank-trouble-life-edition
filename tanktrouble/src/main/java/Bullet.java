/**
 * bullet
 *
 * @author adam
 */
public class Bullet extends DynamicObject {
    /**
     * owner id
     */
    public Tank owner;
    /**
     * timestamp
     */
    private long timestamp;

    /**
     * default constructor
     */
    public Bullet(Tank owner) {
        super();
        // set tank that fired this
        this.owner = owner;
        // start time to Constants.BULLET_DURATION
        timestamp = System.nanoTime();
        // perpetual motion
        north = true;
    }

    @Override
    public void act() {
        super.act();
        // check lifetime
        if (1.0 * (System.nanoTime() - timestamp) / 1000000000L > Constants.BULLET_DURATION) {
            kill();
        }
    }

    @Override
    public void actOn(Object other) {
        // don't actOn with self or owner
        if (id == other.id || owner.id == other.id) {
            return;
        }
        // kill
        if (edgeToEdgeDistance(other) <= 0 && other instanceof Tank) {
            other.kill();
            kill();
        }
    }

    @Override
    public void kill() {
        super.kill();
        // reload one bullet for owner
        owner.bullets--;
        // accumulate owner score
        owner.score += Constants.BULLET_SCORE;
    }

    /**
     * bounce
     */
    @Override
    protected void reverse() {
        // hard code for angle of reflection of four walls
        // evaluate four corners
        if (Math.abs(translate.getX()) < 10 && Math.abs(translate.getY()) < 10) {
            rotate.setAngle(45);
        } else if (Math.abs(translate.getX()) < 10 && Math.abs(translate.getY() - Constants.HEIGHT) < 10) {
            rotate.setAngle(315);
        } else if (Math.abs(translate.getX() - Constants.WIDTH) < 10 && Math.abs(translate.getY() - Constants.HEIGHT) < 10) {
            rotate.setAngle(235);
        } else if (Math.abs(translate.getX() - Constants.WIDTH) < 10 && Math.abs(translate.getY()) < 10) {
            rotate.setAngle(135);
        } else if (Math.abs(translate.getX() - Constants.WIDTH) < 10) {
            if (rotate.getAngle() > 270 && rotate.getAngle() < 360) {
                rotate.setAngle(540 - rotate.getAngle());
            } else {
                rotate.setAngle(180 - rotate.getAngle());
            }
        } else if (Math.abs(translate.getX()) < 10) {
            if (rotate.getAngle() > 90 && rotate.getAngle() < 180) {
                rotate.setAngle(180 - rotate.getAngle());
            } else {
                rotate.setAngle(540 - rotate.getAngle());
            }
        } else if (Math.abs(translate.getY()) < 10) {
            if (rotate.getAngle() > 180 && rotate.getAngle() < 270) {
                rotate.setAngle(360 - rotate.getAngle());
            } else {
                rotate.setAngle(360 - rotate.getAngle());
            }
        } else if (Math.abs(translate.getY() - 900) < 10) {
            if (rotate.getAngle() > 180 && rotate.getAngle() < 270) {
                rotate.setAngle(360 - rotate.getAngle());
            } else {
                rotate.setAngle(360 - rotate.getAngle());
            }
        } else {
            // temporary fix to collision with objects
            rotate.setAngle(rotate.getAngle() + 90);
        }
    }

    @Override
    protected void goNorth() {
        translate.setX(translate.getX() + movementMultiplier * BULLET_MOVEMENT * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() + movementMultiplier * BULLET_MOVEMENT * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goSouth() {
        translate.setX(translate.getX() - movementMultiplier * BULLET_MOVEMENT * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() - movementMultiplier * BULLET_MOVEMENT * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goEast() {
        rotate.setAngle(rotate.getAngle() + movementMultiplier * TURN_MULTIPLIER * BULLET_MOVEMENT);
    }

    @Override
    protected void goWest() {
        rotate.setAngle(rotate.getAngle() - movementMultiplier * TURN_MULTIPLIER * BULLET_MOVEMENT);
    }
}
