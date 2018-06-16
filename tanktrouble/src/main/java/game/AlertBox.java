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
        Stage window = new Stage();//create new stage
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game has finished...");//stage title
        //stage properties
        window.setMinWidth(500);
        window.setMinHeight(300);

        //crete label to display message
        Label label = new Label(message);
        //create return button to return to menu
        Button returnButton = new Button("Return to Menu");
        //add functionality
        returnButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                window.close();
                Main.runMenu();
            }
        }
        );

        //create layout to place components
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,returnButton);//add components
        layout.setAlignment(Pos.CENTER);
        //set up scene
        Scene popUp = new Scene(layout);
        window.setScene(popUp);//add to stage
        window.show();//show window
    }
}
