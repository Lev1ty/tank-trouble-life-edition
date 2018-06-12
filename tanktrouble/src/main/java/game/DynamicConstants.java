package game;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public interface DynamicConstants extends Constants {
    /**
     * pane
     */
    Pane pane = new Pane();
    /**
     * scene
     */
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
}
