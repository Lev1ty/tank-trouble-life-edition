import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public abstract class DynamicObject extends Object {
    public double step;
    public Rotate rotate;
    public Translate translate;
    public boolean north, south, east, west;

    public DynamicObject(String imagePath, double step) {
        this(imagePath, 0, 0, step);
        int[] position = generateRandomPosition();
        translate.setX(position[0]);
        translate.setY(position[1]);
    }

    public DynamicObject(String imagePath, double x, double y, double step) {
        super(imagePath);
        rotate = new Rotate();
        translate = new Translate();
        translate.setX(x);
        translate.setY(y);
        rotate.setPivotX(translate.getX() + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(translate.getY() + imageView.getImage().getHeight() / 2);
        imageView.getTransforms().addAll(rotate, translate);
        this.step = step;
    }

    public DynamicObject(DynamicObject other, String imagePath, double step) {
        this(imagePath, other.translate.getX(), other.translate.getY(), step);
        rotate.setAngle(other.rotate.getAngle());
    }

    public void initializeForFrame() {
        updateRotatePivot();
    }

    public void updateRotatePivot() {
        rotate.setPivotX(translate.getX() + imageView.getImage().getWidth() / 2);
        rotate.setPivotY(translate.getY() + imageView.getImage().getHeight() / 2);
    }

    public void goNorth() {
        translate.setX(translate.getX() + step * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() + step * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    public void goSouth() {
        translate.setX(translate.getX() - step * Math.cos(rotate.getAngle() * Math.PI / 180));
        translate.setY(translate.getY() - step * Math.sin(rotate.getAngle() * Math.PI / 180));
    }

    public void goEast() {
        rotate.setAngle(rotate.getAngle() + step);
    }

    public void goWest() {
        rotate.setAngle(rotate.getAngle() - step);
    }
}
