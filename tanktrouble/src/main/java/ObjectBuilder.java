import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * object builder
 *
 * @author adam
 */
public abstract class ObjectBuilder extends Object {
    /**
     * set translate to random non overlapping position
     */
    public ObjectBuilder setTranslateToRandomNonOverlappingPosition() {
        // initialize to random position
        this.translate.setX(Constants.randomRange(0, (int) (WIDTH - this.imageView.getImage().getWidth())));
        this.translate.setY(Constants.randomRange(0, (int) (HEIGHT - this.imageView.getImage().getHeight())));
        // generate until random does not overlap
        while (true) {
            // initialize to no overlap
            boolean good = true;
            // check for overlap
            for (Object object : ObjectBuilder.global) {
                if (id != object.id && this.edgeToEdgeDistance(object) < 0) {
                    // there is overlap
                    good = false;
                    // generate another random position
                    this.translate.setX(Constants.randomRange(0, (int) (WIDTH - this.imageView.getImage().getWidth())));
                    this.translate.setY(Constants.randomRange(0, (int) (HEIGHT - this.imageView.getImage().getHeight())));
                    break;
                }
            }
            // break if no overlap
            if (good) {
                return this;
            }
        }
    }

    /**
     * add image view to pane
     */
    public ObjectBuilder addImageViewToPane() {
        this.pane.getChildren().add(this.imageView);
        return this;
    }

    /**
     * add translate to image view
     */
    public ObjectBuilder addTranslateToImageView() {
        this.imageView.getTransforms().add(this.translate);
        return this;
    }

    /**
     * add rotate to image view
     */
    public ObjectBuilder addRotateToImageView() {
        this.imageView.getTransforms().add(this.rotate);
        return this;
    }

    /**
     * set pane
     */
    public ObjectBuilder setPane(Pane pane) {
        this.pane = pane;
        return this;
    }

    /**
     * set image view
     */
    public ObjectBuilder setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    /**
     * set translate
     */
    public ObjectBuilder setTranslate(Translate translate, double x, double y) {
        this.translate = translate;
        this.translate.setX(x);
        this.translate.setY(y);
        return this;
    }

    /**
     * set translate
     */
    public ObjectBuilder setTranslate(Translate translate) {
        return setTranslate(translate, translate.getX(), translate.getY());
    }

    /**
     * set rotate
     */
    public ObjectBuilder setRotate(Rotate rotate, double x, double y, double d) {
        this.rotate = rotate;
        this.rotate.setPivotX(x);
        this.rotate.setPivotY(y);
        this.rotate.setAngle(d);
        return this;
    }

    /**
     * set rotate
     */
    public ObjectBuilder setRotate(Rotate rotate) {
        return setRotate(rotate, rotate.getPivotX(), rotate.getPivotY(), rotate.getAngle());
    }
}
