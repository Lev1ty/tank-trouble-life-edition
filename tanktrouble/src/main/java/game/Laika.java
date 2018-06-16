package game;

/**
 * Algorithm based computer tank (current version)
 */

public class Laika extends Tank {

    private int stuckCount;//count the approximate number of times the tank got stuck

    public Laika() {
        super();
        stuckCount = 0;
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
        if (inTrouble()) {//tank is not safe
            north = true;//start moving north

            for (Object o : Object.global) {//for all objects on the screen
                if (o instanceof Bush && !o.dead) {//if object is a bush
                    if(this.edgeToEdgeDistance(o) <= 2 && stuckCount < 5){//if very close try going south
                        north = false;
                        south = true;
                        stuckCount++;
                    }
                    else if (this.edgeToEdgeDistance(o) <= 5 && stuckCount < 10) {//if close try going east
                        north = false;
                        east = true;
                        //south = true;
                        stuckCount++;
                    }

                }
                else if(o instanceof Tank && !o.dead && ((Tank)o).id != this.id){//if object is another tank
                    if(this.edgeToEdgeDistance(o) <= 2 && stuckCount < 5){//if very close move south
                        north = false;
                        south = true;
                        stuckCount++;
                    }
                    else if (this.edgeToEdgeDistance(o) <= 5 && stuckCount < 10) {//if close then try going east
                        north = false;
                        east = true;
                        //south = true;
                        stuckCount++;
                    }
                }
            }
            //if the tank is nearing the edge of the map/screen
            if(Math.abs(translate.getX()-WIDTH) < 3*epsilon || Math.abs(translate.getY()-HEIGHT) < 3*epsilon || Math.abs(translate.getY()) < 3*epsilon || Math.abs(translate.getX()) < 3*epsilon){
                fire = true;//fire bullets to kill nearby tanks
                north = false;
                south = false;
                if(rotateTank(WIDTH/2,HEIGHT/2)){//rotate to the middle of the screen
                    north = true;//then move north
                }
            }
            if(north){//no longer stuck since tank is moving north
                stuckCount = 0;
            }
        } else {//if tank is safe
            fireTank();//fire bullets
        }
    }

    /**
     * fire the tanks bullets
     */
    private void fireTank() {
        double distance = INF;//distance to closest tank
        double coordX = 0, coordY = 0;//coordinates of closest tank

        for (Object o : Object.global) {//for every object on the screen
            if (o instanceof Player || o instanceof Laika) {//if object is a tank
                if (this.id != o.id && !o.dead) {//if object is another tank and not dead
                    //compute distance between this tank and the other tank
                    double dist = Math.pow(this.translate.getX() - o.translate.getX(), 2) + Math.pow(this.translate.getY() - o.translate.getY(), 2);

                    //if smaller than current distance, then update location of closest tank
                    if (dist < distance) {
                        distance = dist;
                        coordX = o.translate.getX();
                        coordY = o.translate.getY();
                    }
                }
            }
        }

        if (distance == INF) {//don't fire if no tanks
            fire = false;
        } else {
            if (rotateTank(coordX, coordY))//if tank is at appropriate angle
                fire = true;//fire away
            else
                fire = false;
        }
    }

    /**
     * rotate tank to appropriate angle to fire bullets
     * @param coordX
     * @param coordY
     * @return
     */

    private boolean rotateTank(double coordX, double coordY) {
        //compute the angle at which tank should fire
        double angle = HALF_TURN * Math.atan(Math.abs((coordY - this.translate.getY()) / (coordX - this.translate.getX()))) / Math.PI;

        //use CAST to determine real angle (adjusted)
        if (coordY < this.translate.getY() && coordX > this.translate.getX()) {
            angle = FULL_TURN - angle;
        } else if (coordY < this.translate.getY() && coordX < this.translate.getX()) {
            angle = HALF_TURN + angle;
        } else if (coordY > this.translate.getY() && coordX < this.translate.getX()) {
            angle = HALF_TURN - angle;
        }

        //determine current firing angle
        double fireAngle = this.rotate.getAngle() % FULL_TURN;


        if (Math.abs(angle - fireAngle) > (TURN_MULTIPLIER * TANK_MOVEMENT)) {//if the tank's gun is not on target yet
            if (angle > fireAngle) {//if angle exceeds fireAngle
                this.east = true;//moving east is faster
            } else {
                this.west = true;//moving west is faster
            }
            return false;
        } else {//tank's gun is on target
            return true;
        }
    }

    /**
     * determines if tank is safe
     * @return
     */
    private boolean inTrouble() {
        for (Object o : Object.global) {//for every object on screen
            if (o instanceof Bullet && !o.dead && ((Bullet) o).owner.id != this.id) {//if another tank's bullet
                //if the bullet is within a certain area
                if (Math.abs(o.translate.getX() - this.translate.getX()) < 50 || Math.abs(o.translate.getY() - this.translate.getY()) < 50) {
                    return true;//tank is not safe
                }
            }
        }
        return false;
    }

}
