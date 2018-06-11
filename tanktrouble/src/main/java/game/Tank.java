package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * tank
 *
 * @author adam
 */
public abstract class Tank extends DynamicObject {
    /**
     * fire flag
     */
    public boolean fire;
    /**
     * bullet counter
     */
    public int bullets;
    /**
     * score
     */
    public int score;
    /**
     * last fire timestamp
     */
    public long lastFireTime;

    /**
     * default constructor
     */
    public Tank() {
        super();
        // zero bullets to begin with
        bullets = 0;
        // zero score to begin with
        score = 0;
        // never fired
        lastFireTime = 0;
    }

    @Override
    public void act() {
        super.act();
        // nullify fire if threshold reached
        if (bullets >= BULLET_COUNT
                || 1.0 * (System.nanoTime() - lastFireTime) / 1000000000L <= RELOAD_TIME) {
            fire = false;
        }
        // fire
        if (fire) {
            // reset trigger
            fire = false;
            // set last fire time to now
            lastFireTime = System.nanoTime();
            // increment bullet counter
            bullets++;
            // spawn bullet
            ObjectBuilder objectBuilder = new Bullet(this).setImageView(new ImageView(new Image("bullet.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate(), rotate.getPivotX(), rotate.getPivotY(), rotate.getAngle())
                    .addRotateToImageView();
            objectBuilder.setTranslate(new Translate(),
                    translate.getX() + imageView.getImage().getWidth() / 2 - objectBuilder.imageView.getImage().getWidth() / 2,
                    translate.getY() + imageView.getImage().getHeight() / 2 - objectBuilder.imageView.getImage().getHeight() / 2)
                    .addTranslateToImageView()
                    .buffer();
        }
    }

    @Override
    public void actOn(Object other) {
        // don't act on self
        if (id == other.id) {
            return;
        }
        // act on non-bullet
        if ((other instanceof DynamicObject) && !(other instanceof Bullet) && edgeToEdgeDistance(other) <= 0) {
            ((DynamicObject) other).reverse();
        }
    }

    /**
     * stuck
     */
    @Override
    protected void reverse() {
        snd.playCollideSound();
        // constrain to one direction to avoid gravity bug
        // gravity bug is when reversing in two directions
        // actually pulls this towards the forward direction
        if (north & east) {
            east = false;
        }
        if (north & west) {
            west = false;
        }
        if (south & east) {
            east = false;
        }
        if (south & west) {
            west = false;
        }
        // counteract heading to simulate begin stuck
        if (north) {
            goSouth();
        }
        if (south) {
            goNorth();
        }
        if (east) {
            goWest();
        }
        if (west) {
            goEast();
        }
    }

    @Override
    protected void goNorth() {
        translate.setX(translate.getX() + movementMultiplier * TANK_MOVEMENT * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() + movementMultiplier * TANK_MOVEMENT * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goSouth() {
        translate.setX(translate.getX() - movementMultiplier * TANK_MOVEMENT * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() - movementMultiplier * TANK_MOVEMENT * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goEast() {
        rotate.setAngle(rotate.getAngle() + movementMultiplier * TURN_MULTIPLIER * TANK_MOVEMENT);
    }

    @Override
    protected void goWest() {
        rotate.setAngle(rotate.getAngle() - movementMultiplier * TURN_MULTIPLIER * TANK_MOVEMENT);
    }
}
