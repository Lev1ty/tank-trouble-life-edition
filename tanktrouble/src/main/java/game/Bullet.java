package game;

/**
 * bullet
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
        // start time to game.Constants.BULLET_DURATION
        timestamp = System.nanoTime();
        // perpetual motion
        north = true;
    }

    @Override
    public void act() {
        super.act();
        // check lifetime
        if (1.0 * (System.nanoTime() - timestamp) / 2000000000L > Constants.BULLET_DURATION) {
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
            killPlayer();
            //add Sound
            snd.playTankDestroy();
        }
    }

    @Override
    protected void kill() {
        super.kill();
        // reload one bullet for owner
        owner.bullets--;
    }

    private void killPlayer() {
        kill();
        // accumulate owner score
        owner.score += Constants.BULLET_SCORE;
    }

    /**
     * method to describe how bullets bounce off walls
     */
    @Override
    protected void reverse() {
        // hard code for angle of reflection of four walls
        // use newton's law of reflection to evaluate reflection off of walls
        // case work necessary to invert angles
        // evaluate four corners and reflect accordingly
        if (Math.abs(this.translate.getX()) < epsilon && Math.abs(this.translate.getY()) < epsilon) {
            this.rotate.setAngle(SOUTH_EAST);
        } else if (Math.abs(this.translate.getX()) < epsilon && Math.abs(this.translate.getY() - HEIGHT) < epsilon) {
            this.rotate.setAngle(NORTH_EAST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon && Math.abs(this.translate.getY() - HEIGHT) < epsilon) {
            this.rotate.setAngle(NORTH_WEST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon && Math.abs(this.translate.getY()) < epsilon) {
            this.rotate.setAngle(SOUTH_WEST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon) {//if bullet hits right wall
            if (this.rotate.getAngle() > THREE_QUART_TURN && this.rotate.getAngle() < FULL_TURN) {
                this.rotate.setAngle(THREE_HALF_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(HALF_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getX()) < epsilon) {//if bullet hits left wall
            if (this.rotate.getAngle() > QUART_TURN && this.rotate.getAngle() < HALF_TURN) {
                this.rotate.setAngle(HALF_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(THREE_HALF_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY()) < epsilon) {//if bullet hits top wall
            if (this.rotate.getAngle() > HALF_TURN && this.rotate.getAngle() < THREE_QUART_TURN) {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY() - HEIGHT) < epsilon) {//if bullet hits bottom wall
            if (this.rotate.getAngle() > HALF_TURN && this.rotate.getAngle() < THREE_QUART_TURN) {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            }
        } else {//if hits undefined area
            this.rotate.setAngle(this.rotate.getAngle() + QUART_TURN);//rotate bullet by 90 degrees
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
        rotate.setAngle(rotate.getAngle() + movementMultiplier * TURN_MULTIPLIER * BULLET_MOVEMENT);//increase angle
    }

    @Override
    protected void goWest() {
        rotate.setAngle(rotate.getAngle() - movementMultiplier * TURN_MULTIPLIER * BULLET_MOVEMENT);//decrease angle
    }
}
