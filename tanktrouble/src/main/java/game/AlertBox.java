package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        //initialize and create back button image
        Image backButton = new Image ("back.png");
        ImageView back = new ImageView (backButton);
        //adjust image size
        back.setFitHeight(80);
        back.setFitWidth(180);

        //crete label to display message
        Label label = new Label(message);
        //change display font size
        label.setFont(Font.font("",20));
        //create return button to return to menu
        Button returnButton = new Button("", back);

        //add functionality
        //click return button
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
