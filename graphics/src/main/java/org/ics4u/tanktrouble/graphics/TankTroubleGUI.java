package org.ics4u.tanktrouble.graphics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.ics4u.tanktrouble.mechanics.buckets.*;
import org.ics4u.tanktrouble.mechanics.bullets.Bullet;
import org.ics4u.tanktrouble.mechanics.bullets.Standard;
import org.ics4u.tanktrouble.mechanics.meta.vectors.MovementVector;

public class TankTroubleGUI extends Application {

    @FXML
    private Pane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TankTroubleGUI.fxml"));
        Scene primaryScene = new Scene(root);
        primaryStage.setTitle("Tank Trouble Life Edition");
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void onMouseClickedStartButton() {
        pane.getChildren().clear();
//        TankTroubleBucket tanks = new Tanks(pane, 3, 10);
//        TankTroubleBucket powerups = new Powerups(pane, 5);
//        TankTroubleBucket obstacles = new Obstacles(pane, 8);
//        TankTroubleBucket bullets = new Bullets(pane, 20);
        Bullet bullet = new Standard(pane);
        bullet.movement = new MovementVector(1, 1);
    }
}
