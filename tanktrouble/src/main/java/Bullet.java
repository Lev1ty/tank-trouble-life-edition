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
        if (Math.abs(this.translate.getX()) < 10 && Math.abs(this.translate.getY()) < 10) {
            this.rotate.setAngle(45);
        } else if (Math.abs(this.translate.getX()) < 10 && Math.abs(this.translate.getY() - 900) < 10) {
            this.rotate.setAngle(315);
        } else if (Math.abs(this.translate.getX() - 1600) < 10 && Math.abs(this.translate.getY() - 900) < 10) {
            this.rotate.setAngle(235);
        } else if (Math.abs(this.translate.getX() - 1600) < 10 && Math.abs(this.translate.getY()) < 10) {
            this.rotate.setAngle(135);
        } else if (Math.abs(this.translate.getX() - 1600) < 10) {
            if (this.rotate.getAngle() > 270 && this.rotate.getAngle() < 360) {
                this.rotate.setAngle(540 - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(180 - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getX()) < 10) {
            if (this.rotate.getAngle() > 90 && this.rotate.getAngle() < 180) {
                this.rotate.setAngle(180 - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(540 - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY()) < 10) {
            if (this.rotate.getAngle() > 180 && this.rotate.getAngle() < 270) {
                this.rotate.setAngle(360 - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(360 - this.rotate.getAngle());
            }
        } else if (Math.abs(this.translate.getY() - 900) < 10) {
            if (this.rotate.getAngle() > 180 && this.rotate.getAngle() < 270) {
                this.rotate.setAngle(360 - this.rotate.getAngle());
            } else {
                this.rotate.setAngle(360 - this.rotate.getAngle());
            }
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
