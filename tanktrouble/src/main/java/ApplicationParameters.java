import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * application constants
 *
 * @author adam
 */
public interface ApplicationParameters {
    /**
     * title of window
     */
    String TITLE = "Tank Trouble Skynet";
    /**
     * width of window
     */
    int WIDTH = 1600;
    /**
     * height of window
     */
    int HEIGHT = 900;
    /**
     * excess allocated pixels due to fixed window dimensions
     * <p>
     * primaryStage.setResizable(false);
     *
     * @see Main
     */
    int SPILL = 15;
    /**
     * amount of human players
     */
    int PLAYER_COUNT = 2;
    /**
     * action screen
     */
    Pane pane = new Pane();
    /**
     * integration layer
     * <p>
     * event listeners etc.
     */
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
    int AI_COUNT = 30;
}
