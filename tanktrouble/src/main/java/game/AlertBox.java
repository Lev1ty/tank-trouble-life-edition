package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * class to create a separate window when game ends
 */

public class AlertBox {
    public static void display(String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game has finished...");
        window.setMinWidth(500);
        window.setMinHeight(300);

        Label label = new Label(message);
        Button returnButton = new Button("Return to Menu");
        returnButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                window.close();
                Main.runMenu();
            }
        }
        );

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,returnButton);
        layout.setAlignment(Pos.CENTER);

        Scene popUp = new Scene(layout);
        window.setScene(popUp);
        window.show();
    }
}
