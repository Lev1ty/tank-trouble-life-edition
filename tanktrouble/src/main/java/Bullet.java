import javafx.geometry.*;
import java.lang.Math.*;
/**
 * bullet
 *
 * @author adam
 */
public class Bullet extends DynamicObject {
    /**
     * owner id
     */
    public int ownerId;

    /**
     * default constructor
     */
    public Bullet(int ownerId) {
        super();
        this.ownerId = ownerId;
    }

    @Override
    public void act() {
        this.updateRotatePivot();
        if (this.outOfBounds()) {
            this.bounce();
        }
        this.goNorth();
    }

    @Override
    public void interact(Object other) {
        if (this.id == other.id || this.ownerId == other.id) {
            return;
        }
        if (this.edgeToEdgeDistance(other) <= 0) {
            this.bounce();
        }
    }

    /**
     * bounce
     */
    private void bounce() {
        // evaluate four corners
        if (Math.abs(this.translate.getX()) < epsilon && Math.abs(this.translate.getY()) < epsilon) {
            this.rotate.setAngle(SOUTH_EAST);
        } else if (Math.abs(this.translate.getX()) < epsilon && Math.abs(this.translate.getY() - HEIGHT) < epsilon) {
            this.rotate.setAngle(NORTH_EAST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon && Math.abs(this.translate.getY() - HEIGHT) < epsilon) {
            this.rotate.setAngle(NORTH_WEST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon && Math.abs(this.translate.getY()) < epsilon) {
            this.rotate.setAngle(SOUTH_WEST);
        } else if (Math.abs(this.translate.getX() - WIDTH) < epsilon) {
            if (this.rotate.getAngle() > THREE_QUART_TURN && this.rotate.getAngle() < FULL_TURN) {
                this.rotate.setAngle(THREE_HALF_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(HALF_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getX()) < epsilon) {
            if (this.rotate.getAngle() > QUART_TURN && this.rotate.getAngle() < HALF_TURN) {
                this.rotate.setAngle(HALF_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(THREE_HALF_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY()) < epsilon) {
            if (this.rotate.getAngle() > HALF_TURN && this.rotate.getAngle() < THREE_QUART_TURN) {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY() - HEIGHT) < epsilon) {
            if (this.rotate.getAngle() > HALF_TURN && this.rotate.getAngle() < THREE_QUART_TURN) {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(FULL_TURN - this.rotate.getAngle());
            }
        } else {
            this.rotate.setAngle(this.rotate.getAngle() + QUART_TURN);
        }
    }

    @Override
    protected void goNorth() {
        this.translate.setX(this.translate.getX() + Constants.BULLET_MOVEMENT * Math.cos(this.rotate.getAngle() * Math.PI / 180));
        this.translate.setY(this.translate.getY() + Constants.BULLET_MOVEMENT * Math.sin(this.rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goSouth() {
        this.translate.setX(this.translate.getX() - Constants.BULLET_MOVEMENT * Math.cos(this.rotate.getAngle() * Math.PI / 180));
        this.translate.setY(this.translate.getY() - Constants.BULLET_MOVEMENT * Math.sin(this.rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goEast() {
        this.rotate.setAngle(this.rotate.getAngle() + Constants.BULLET_MOVEMENT);
    }

    @Override
    protected void goWest() {
        this.rotate.setAngle(this.rotate.getAngle() - Constants.BULLET_MOVEMENT);
    }
}
