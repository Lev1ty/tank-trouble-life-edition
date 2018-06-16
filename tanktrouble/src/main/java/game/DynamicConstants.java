package game;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface DynamicConstants extends Constants {
    /**
     * pane for game
     */
    Pane pane = new Pane();
    /**
     * scene for game
     */
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
    /**
     * menu stack pane
     */
    Pane menuPane = new Pane();
    /**
     * menu scene
     */
    Scene menu = new Scene(menuPane,WIDTH,HEIGHT);
    /**
     * instructions stack pane
     */
    StackPane instructionsPane = new StackPane();
    /**
     * instructions scene
     */
    Scene instructions = new Scene(instructionsPane, WIDTH, HEIGHT);
    /**
     * player select pane
     */
    StackPane playerSelectPane = new StackPane();
    /**
     * player select scene
     */
    Scene playerSelect = new Scene(playerSelectPane, WIDTH, HEIGHT);
}
