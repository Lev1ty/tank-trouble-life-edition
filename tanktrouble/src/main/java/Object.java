import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * object
 *
 * @author adam
 */
public abstract class Object implements Constants {
    /**
     * global array
     */
    public static List<Object> global = new ArrayList<>();
    /**
     * global buffer array
     * <p>
     * dynamically generated objects are pushed to buffer during the frame to avoid concurrent modification exception
     */
    public static List<Object> buffer = new ArrayList<>();
    /**
     * id count
     */
    public static int idCount = 0;
    /**
     * id
     */
    public int id;
    /**
     * active status
     */
    public boolean active;
    /**
     * dead status
     */
    public boolean dead;
    /**
     * pane
     */
    public Pane pane;
    /**
     * image view
     */
    public ImageView imageView;
    /**
     * translation
     */
    public Translate translate;
    /**
     * rotate
     */
    public Rotate rotate;

    /**
     * default constructor
     */
    public Object() {
        // set id
        this.id = ObjectBuilder.idCount;
        // update id count
        idCount++;
        // default inactive
        this.active = false;
        // default alive
        this.dead = false;
    }

    /**
     * interact with this
     */
    public abstract void act();

    /**
     * interact with other object
     */
    public abstract void interact(Object other);

    /**
     * center to center distance
     */
    public double centerToCenterDistance(Object other) {
        return Math.sqrt(Math.pow((this.translate.getX() + this.imageView.getImage().getWidth() / 2)
                - (other.translate.getX() + other.imageView.getImage().getWidth() / 2), 2)
                + Math.pow((this.translate.getY() + this.imageView.getImage().getHeight() / 2)
                - (other.translate.getY() + other.imageView.getImage().getHeight() / 2), 2));
    }

    /**
     * edge to edge distance
     */
    public double edgeToEdgeDistance(Object other) {
        return this.centerToCenterDistance(other)
                - this.imageView.getImage().getWidth() / 2
                - other.imageView.getImage().getWidth() / 2;
    }

    /**
     * activate
     */
    public void activate() {
        // add this to global array
        ObjectBuilder.global.add(this);
        // activate object
        this.active = true;
    }

    /**
     * push to buffer
     */
    public void buffer() {
        // add this to buffer array
        ObjectBuilder.buffer.add(this);
    }

    /**
     * deactivate
     */
    public void kill() {
        // update state
        this.active = false;
        this.dead = true;
        // remove from window
        this.pane.getChildren().remove(this.imageView);
    }
}
