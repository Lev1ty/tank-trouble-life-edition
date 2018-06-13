package game;

/**
 * Algorithm based computer tank
 */

public class Laika extends Tank {

    public Laika() {
        super();
    }

    @Override
    public void act() {
        super.act();
        // reset all flags
        // necessary because no external cues (key lift) to hint heading change
        north = false;
        south = false;
        east = false;
        west = false;
        fire = false;
        if (inTrouble()) {
            this.north = true;
//           this.goNorth();
        } else {
            fireTank();
        }
    }

    private void fireTank() {
        double distance = INF;
        double coordX = 0, coordY = 0;
        for (Object o : Object.global) {
            if (o instanceof Player || o instanceof Laika) {
                if (this.id != o.id && !o.dead) {
                    double dist = Math.pow(this.translate.getX() - o.translate.getX(), 2) + Math.pow(this.translate.getY() - o.translate.getY(), 2);

                    if (dist < distance) {
                        distance = dist;
                        coordX = o.translate.getX();
                        coordY = o.translate.getY();
                    }
                }
            }
        }

        if (distance == INF) {
            fire = false;
        } else {
            if (rotateTank(coordX, coordY))
                fire = true;
            else
                fire = false;
        }
    }

    private boolean rotateTank(double coordX, double coordY) {
        double angle = HALF_TURN * Math.atan(Math.abs((coordY - this.translate.getY()) / (coordX - this.translate.getX()))) / Math.PI;

        if (coordY < this.translate.getY() && coordX > this.translate.getX()) {
            angle = FULL_TURN - angle;
        } else if (coordY < this.translate.getY() && coordX < this.translate.getX()) {
            angle = HALF_TURN + angle;
        } else if (coordY > this.translate.getY() && coordX < this.translate.getX()) {
            angle = HALF_TURN - angle;
        }

        double fireAngle = this.rotate.getAngle() % FULL_TURN;
        if (Math.abs(angle - fireAngle) > (TURN_MULTIPLIER * TANK_MOVEMENT)) {
            if (angle > fireAngle) {
//                this.goEast();
                this.east = true;
            } else {
//                this.goWest();
                this.west = true;
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean inTrouble() {
        for (Object o : Object.global) {
            if (o instanceof Bullet && !o.dead && ((Bullet) o).owner.id != this.id) {
                if (Math.abs(o.translate.getX() - this.translate.getX()) < 50 || Math.abs(o.translate.getY() - this.translate.getY()) < 50) {
                    return true;
                }
            }
        }
        return false;
    }

}
