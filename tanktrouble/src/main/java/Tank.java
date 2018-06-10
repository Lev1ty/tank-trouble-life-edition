import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * tank
 *
 * @author adam
 */
public class Tank extends DynamicObject {
    /**
     * fire flag
     */
    public boolean fire;
    /**
     * AI flag
     */
    public boolean ai;

    /**
     * default constructor
     */
    public Tank(boolean ai) {
        super();
        this.ai = ai;
    }

    @Override
    public void act() {
        this.updateRotatePivot();
        if (this.north) {
            this.goNorth();
        }
        if (this.south) {
            this.goSouth();
        }
        if (this.east) {
            this.goEast();
        }
        if (this.west) {
            this.goWest();
        }
        if (this.outOfBounds()) {
            this.stuck();
        }
    }

    @Override
    public void interact(Object other) {
        // don't interact with self
        if (this.id == other.id) {
            return;
        }
        // collision
        if (this.edgeToEdgeDistance(other) <= 0) {
            this.stuck();
        }
        // fire
        if (this.fire) {
            // reset trigger
            this.fire = false;
            // spawn bullet
            ObjectBuilder objectBuilder = new Bullet(this.id).setImageView(new ImageView(new Image("bullet.png")))
                    .setPane(Constants.pane).addImageViewToPane()
                    .setRotate(new Rotate(), this.rotate.getPivotX(), this.rotate.getPivotY(), this.rotate.getAngle())
                    .addRotateToImageView();
            objectBuilder.setTranslate(new Translate(),
                    this.translate.getX() + this.imageView.getImage().getWidth() / 2 - objectBuilder.imageView.getImage().getWidth() / 2,
                    this.translate.getY() + this.imageView.getImage().getHeight() / 2 - objectBuilder.imageView.getImage().getHeight() / 2)
                    .addTranslateToImageView()
                    .buffer();
        }
    }

    /**
     * stuck
     */
    private void stuck() {
        if (this.north & this.east) this.east = false;
        if (this.north & this.west) this.west = false;
        if (this.south & this.east) this.east = false;
        if (this.south & this.west) this.west = false;
        if (this.north) this.goSouth();
        if (this.south) this.goNorth();
        if (this.east) this.goWest();
        if (this.west) this.goEast();
    }

    @Override
    protected void goNorth() {
        this.translate.setX(this.translate.getX() + Constants.TANK_MOVEMENT * Math.cos(this.rotate.getAngle() * Math.PI / 180));
        this.translate.setY(this.translate.getY() + Constants.TANK_MOVEMENT * Math.sin(this.rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goSouth() {
        this.translate.setX(this.translate.getX() - Constants.TANK_MOVEMENT * Math.cos(this.rotate.getAngle() * Math.PI / 180));
        this.translate.setY(this.translate.getY() - Constants.TANK_MOVEMENT * Math.sin(this.rotate.getAngle() * Math.PI / 180));
    }

    @Override
    protected void goEast() {
        this.rotate.setAngle(this.rotate.getAngle() + Constants.TANK_MOVEMENT);
    }

    @Override
    protected void goWest() {
        this.rotate.setAngle(this.rotate.getAngle() - Constants.TANK_MOVEMENT);
    }
}
