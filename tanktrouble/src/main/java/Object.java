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
     * dynamically generated objects are pushed to buffer during each frame to avoid concurrent modification exception
     */
    public static List<Object> buffer = new ArrayList<>();
    /**
     * id count
     */
    private static int idCount = 0;
    /**
     * id
     */
    public int id;
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
        id = idCount;
        // update id count
        idCount++;
        // default alive
        dead = false;
    }

    /**
     * actOn with this
     */
    public abstract void act();

    /**
     * actOn with other object
     * <p>
     * exert a change on the other object
     */
    public abstract void actOn(Object other);

    /**
     * center to center distance
     */
    public double centerToCenterDistance(Object other) {
        return Math.sqrt(Math.pow((translate.getX() + imageView.getImage().getWidth() / 2)
                - (other.translate.getX() + other.imageView.getImage().getWidth() / 2), 2)
                + Math.pow((translate.getY() + imageView.getImage().getHeight() / 2)
                - (other.translate.getY() + other.imageView.getImage().getHeight() / 2), 2));
    }

    /**
     * edge to edge distance
     */
    public double edgeToEdgeDistance(Object other) {
        return centerToCenterDistance(other)
                - imageView.getImage().getWidth() / 2
                - other.imageView.getImage().getWidth() / 2;
    }

    /**
     * activate
     */
    public void activate() {
        // add this to global array
        global.add(this);
    }

    /**
     * push to buffer
     */
    public void buffer() {
        // add this to buffer array
        buffer.add(this);
    }

    /**
     * deactivate
     */
    public void kill() {
        dead = true;
        // remove from window
        pane.getChildren().remove(imageView);
    }
}
