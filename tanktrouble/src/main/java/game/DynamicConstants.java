package game;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface DynamicConstants extends Constants {
    /**
     * pane
     */
    Pane pane = new Pane();
    /**
     * scene
     */
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
    /**
     * menu stack pane
     */
    StackPane menuPane = new StackPane();
    /**
     * menu scene
     */
    Scene menu = new Scene(menuPane,WIDTH,HEIGHT);
}
