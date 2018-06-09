import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public interface ApplicationParameters {
    String TITLE = "Tank Trouble Skynet";
    int WIDTH = 1200;
    int HEIGHT = 800;
    Pane pane = new Pane();
    Scene scene = new Scene(pane, WIDTH, HEIGHT);
}
