import javafx.geometry.*;
import java.lang.Math.*;
/**
 * bullet class
 *
 * @author adam
 */
public class Bullet extends Friendable {
    /**
     * movement per frame
     */
    public static final double STEP = 3;
    /**
     * tank that fired this bullet
     */
    public int ownerId;
    /**
     * timer
     */
    public long time;
    /**
     * owner
     */
    public Tank owner;

    /**
     * copy constructor
     *
     * @param other     usually the tank that fired it
     * @param imagePath bullet image
     */
    public Bullet(Friendable other, String imagePath) {
        super(other, other.translate.getX() + other.imageView.getImage().getWidth() / 2,
                other.translate.getY() + other.imageView.getImage().getHeight() / 2, imagePath, STEP);
        // be friends with tank that fired this
        becomeFriends(other);
        // set the tank as owner
        ownerId = other.id;
        time = System.nanoTime();
        ((Tank) other).bullets++;
        owner = (Tank) other;
    }

    /**
     * check to kill other object
     *
     * @param other object to check
     * @return kill object or not
     */
    public boolean kill(Object other) {
        return (other instanceof Tank) && !isFriend(other) && hit(other);
    }

    /**
     * check if bounce on object
     *
     * @param other object to check
     * @return bounce or not
     */
    public boolean bounce(Object other) {
        return (other instanceof Bush) && id != other.id && hit(other);
    }

    /**
     * to-do when bounced
     */
    //for collisions with bushes
    public void bounced(Object other) {
       rotate.setAngle(rotate.getAngle()+88);
    }

    public void bounced(){
        //evaluate four corners
        if(Math.abs(translate.getX()) < 10 && Math.abs(translate.getY()) < 10){
            rotate.setAngle(45);
        }
        else if(Math.abs(translate.getX()) < 10 && Math.abs(translate.getY()-900) < 10){
            rotate.setAngle(315);
        }
        else if(Math.abs(translate.getX()-1600) < 10 && Math.abs(translate.getY()-900) < 10){
            rotate.setAngle(235);
        }
        else if(Math.abs(translate.getX()-1600) < 10 && Math.abs(translate.getY()) < 10){
            rotate.setAngle(135);
        }
        else if(Math.abs(translate.getX()-1600) < 10){
            if(rotate.getAngle() > 270 && rotate.getAngle() < 360)
                rotate.setAngle(540-rotate.getAngle());
            else
                rotate.setAngle(180 - rotate.getAngle());
        }
        else if(Math.abs(translate.getX()) < 10){
            if(rotate.getAngle() > 90 && rotate.getAngle() < 180)
                rotate.setAngle(180 - rotate.getAngle());
            else
                rotate.setAngle(540-rotate.getAngle());
        }
        else if(Math.abs(translate.getY()) < 10){
            if(rotate.getAngle() > 180 && rotate.getAngle() < 270)
                rotate.setAngle(360 - rotate.getAngle());
            else
                rotate.setAngle(360-rotate.getAngle());
        }
        else if(Math.abs(translate.getY()-900) < 10){
            if(rotate.getAngle() > 180 && rotate.getAngle() < 270)
                rotate.setAngle(360 - rotate.getAngle());
            else
                rotate.setAngle(360-rotate.getAngle());
        }
    }

    /**
     * head north
     */
    @Override
    public void goNorth() {
        translate.setX(translate.getX() + step * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() + step * Math.sin(rotate.getAngle() * Math.PI / 180));
        if (outOfBounds()) bounced();
    }
}
