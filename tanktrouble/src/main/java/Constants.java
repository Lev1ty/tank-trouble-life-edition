import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.Random;

public interface Constants {
    /**
     * title of window
     */
    String WINDOW_TITLE = "Tank Trouble";
    /**
     * width of window
     */
    int WIDTH = 1600;
    /**
     * height of window
     */
    int HEIGHT = 900;
    /**
     * number of AI tanks
     */
    int AI_COUNT = 20;
    /**
     * number of bushes
     */
    int BUSH_COUNT = 20;
    /**
     * movement multiplier
     */
    double MOVEMENT_MULTIPLIER = 1;
    /**
     * bullet movement
     */
    double BULLET_MOVEMENT = 1.5 * MOVEMENT_MULTIPLIER;
    /**
     * tank movement
     */
    double TANK_MOVEMENT = 1 * MOVEMENT_MULTIPLIER;
    /**
     * pane
     */
    Pane pane = new Pane();
    /**
     * scene
     */
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
    /**
     * random number generator
     */
    Random random = new Random();

    /**
     * cardinal direction constants
     */

    int SOUTH_EAST = 45;

    int SOUTH_WEST = 135;

    int NORTH_WEST = 225;

    int NORTH_EAST = 315;

    /**
     * rotation constants
     */

    int FULL_TURN = 360;

    int HALF_TURN = 180;

    int THREE_QUART_TURN = 270;

    int QUART_TURN = 90;

    int THREE_HALF_TURN = 540;

    /**
     * small values
     */

    int epsilon = 10;

    /**
     * random range
     *
     * @param low lower bound
     * @param high upper bound
     * @return random double within range
     */
    static double randomRange(int low, int high) {
        return Constants.random.nextInt(high - low) + low + Constants.random.nextDouble();
    }
}
