public class Tank extends DynamicObject {
    public static final double STEP = 2;
    public boolean fire;
    public int kills;

    public Tank(String imagePath) {
        super(imagePath, STEP);
        kills = 0;
    }

    public Tank(String imagePath, double x, double y) {
        super(imagePath, x, y, STEP);
    }
}
