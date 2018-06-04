import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TankTroubleGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TankTroubleGUI.fxml"));
        Scene primaryScene = new Scene(root);
        primaryStage.setTitle("Tank Trouble Life Edition");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
