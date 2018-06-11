package game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * object builder
 * <p>
 * use builder paradigm to clarify construction of game objects
 *
 * @author adam
 */
public abstract class ObjectBuilder extends Object {
    /**
     * set translate to random non overlapping position
     */
    public ObjectBuilder setTranslateToRandomNonOverlappingPosition() {
        // initialize to random position
        translate.setX(Constants.randomRange(0, (int) (WIDTH - imageView.getImage().getWidth())));
        translate.setY(Constants.randomRange(0, (int) (HEIGHT - imageView.getImage().getHeight())));
        // generate until random does not overlap
        while (true) {
            // initialize to no overlap
            boolean good = true;
            // check for overlap
            for (Object object : global)
                if (id != object.id && edgeToEdgeDistance(object) < 0) {
                    // there is overlap
                    good = false;
                    // generate another random position
                    translate.setX(Constants.randomRange(0, (int) (WIDTH - imageView.getImage().getWidth())));
                    translate.setY(Constants.randomRange(0, (int) (HEIGHT - imageView.getImage().getHeight())));
                    break;
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
        pane.getChildren().add(imageView);
        return this;
    }

    /**
     * add translate to image view
     */
    public ObjectBuilder addTranslateToImageView() {
        imageView.getTransforms().add(translate);
        return this;
    }

    /**
     * add rotate to image view
     */
    public ObjectBuilder addRotateToImageView() {
        imageView.getTransforms().add(rotate);
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
        translate.setX(x);
        translate.setY(y);
        this.translate = translate;
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
        rotate.setPivotX(x);
        rotate.setPivotY(y);
        rotate.setAngle(d);
        this.rotate = rotate;
        return this;
    }

    /**
     * set rotate
     */
    public ObjectBuilder setRotate(Rotate rotate) {
        return setRotate(rotate, rotate.getPivotX(), rotate.getPivotY(), rotate.getAngle());
    }
}
