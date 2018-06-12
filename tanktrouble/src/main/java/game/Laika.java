package game;

/**
 * Algorithm based computer tank
 *
 */

public class Laika extends Tank{

    public Laika(){
        super();
    }

    @Override
    public void act(){
        super.act();
        double distance = INF;
        double coordX = 0,coordY = 0;
        for(Object o : Object.global){
            if(o instanceof Player || o instanceof Laika){
                if(this.id != o.id && !o.dead){
                    double dist = Math.pow(this.translate.getX()-o.translate.getX(),2)+Math.pow(this.translate.getY()-o.translate.getY(),2);

                    if(dist < distance){
                        distance = dist;
                        coordX = o.translate.getX();
                        coordY = o.translate.getY();
                    }
                }
            }
        }

        if(distance == INF){
            fire = false;
        }
        else{
            rotateTank(coordX,coordY);
            fire = true;
        }
    }

    private void rotateTank(double coordX,double coordY){
        double angle = 180*Math.atan(Math.abs((coordY - this.translate.getY())/(coordX - this.translate.getX())))/Math.PI;

        if(coordY < this.translate.getY() && coordX > this.translate.getX()){
            angle = 360 - angle;
        }
        else if(coordY < this.translate.getY() && coordX < this.translate.getX()){
            angle = 180 + angle;
        }
        else if(coordY > this.translate.getY() && coordX < this.translate.getX()){
            angle = 180 - angle;
        }

        int cnt = 0;
        double fireAngle = this.rotate.getAngle()%360.0;
        while(Math.abs(angle - fireAngle) > (TURN_MULTIPLIER * TANK_MOVEMENT) && cnt < 200){
            cnt++;
            this.goEast();
            fireAngle = this.rotate.getAngle()%360.0;
        }

    }

}
