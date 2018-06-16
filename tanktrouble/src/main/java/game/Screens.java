package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;

public class Screens implements DynamicConstants{

    /**
     * menu
     */
    public void runMenu(Stage window){
        //label for the title of menu
        Label title = new Label("", new ImageView(new Image("title.png")));
        title.setLayoutX(500);
        title.setLayoutY(300);

        //labels are pictures which are displayed as background of menu
        Label background1 = new Label("",new ImageView(new Image("background_1.png")));
        background1.setLayoutX(100);
        background1.setLayoutY(100);

        Label background2 = new Label("",new ImageView(new Image("background_2.png")));
        background2.setLayoutX(600);
        background2.setLayoutY(100);

        Label background3 = new Label("",new ImageView(new Image("blue_player.png")));
        background3.setLayoutX(1200);
        background3.setLayoutY(100);

        //initialize and create play image
        Image playButton = new Image("play.png");
        ImageView play = new ImageView(playButton);
        play.setFitHeight(80);
        play.setFitWidth(180);

        //initialize and create instructions image
        Image insButton = new Image ("instructions.jpg");
        ImageView ins = new ImageView(insButton);
        ins.setFitHeight(80);
        ins.setFitWidth(180);

        //create start button
        Button startButton = new Button("", play);
        startButton.setLayoutX(700);
        startButton.setLayoutY(500);

        //create instructions button
        Button instructionsButton = new Button("", ins);
        instructionsButton.setLayoutX(700);
        instructionsButton.setLayoutY(650);

        //event handler object to add button functionality
        final EventHandler eventHandlers = new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent e) {
                if (e.getSource() == startButton) {
                    runPlayerSelect(window);
                } else if (e.getSource() == instructionsButton) {
                    runInstructions(window);
                }

            }
        };
        //add button functionality
        instructionsButton.setOnAction(eventHandlers);
        startButton.setOnAction(eventHandlers);
        //add to screen
        menuPane.getChildren().addAll(title, startButton, instructionsButton,background1,background2,background3);
        //add screen to window
        window.setScene(menu);
    }

    public void runInstructions(Stage window){
        //create and initialize label for title
        Label title = new Label("Instructions");
        title.setFont(Font.font("Verdana", FontWeight.BOLD,50));
        StackPane.setAlignment(title,Pos.TOP_CENTER);

        //set up webview to load html file
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        URL url = null;
        try {
            url = this.getClass().getClassLoader().getResource("Instructions.html");//load HTML file
        }
        catch(Exception e){//catch exceptions
            e.printStackTrace();
        }
        engine.load(url.toExternalForm());
        //browser properties
        browser.setMinHeight(700);
        browser.setMaxHeight(700);
        browser.setMinWidth(900);
        browser.setMaxWidth(900);

        //create return button
        Button returnButton = new Button("Return to Menu");
        StackPane.setAlignment(returnButton, Pos.BOTTOM_CENTER);

        //initialized button functionality
        returnButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                runMenu(window);
            }
        });

        //add components to pane
        instructionsPane.getChildren().addAll(title,returnButton);
        instructionsPane.getChildren().add(browser);
        //set up screen
        window.setScene(instructions);
    }
    /**
     * player select screen
     */
    public void runPlayerSelect(Stage window){
        //set up screen title
        Label title = new Label("SELECT THE PLAYER COUNT");
        title.setFont(Font.font("Verdana", FontWeight.BOLD,50));
        title.setTextFill(Color.web("#A52A2A"));

        //create three buttons each one designed for single player, two player, and three player game modes
        Button one = new Button("");
        ImageView oneImage = new ImageView(new Image("singleplayer.png"));//add picture
        oneImage.setFitHeight(320);
        oneImage.setFitWidth(175);
        one.setGraphic(oneImage);
        Button two = new Button("");
        ImageView twoImage = new ImageView(new Image("twoplayer.png"));//add picture
        twoImage.setFitHeight(320);
        twoImage.setFitWidth(350);
        two.setGraphic(twoImage);
        Button three = new Button("");
        ImageView threeImage = new ImageView(new Image("threeplayer.png"));//add picture
        threeImage.setFitHeight(320);
        threeImage.setFitWidth(525);
        three.setGraphic(threeImage);

        //set up button properties
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(one, Pos.CENTER_LEFT);
        two.setTranslateX(-150);
        two.setTranslateY(0);
        StackPane.setAlignment(three, Pos.CENTER_RIGHT);

        //add event handler for functionality
        EventHandler eventHandler = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(e.getSource() == one){
                    Main.backend(1);
                    window.setScene(scene);// apply scene to window
                }
                else if(e.getSource() == two){
                    Main.backend(2);
                    window.setScene(scene);// apply scene to window
                }
                else if(e.getSource() == three){
                    Main.backend(3);
                    window.setScene(scene);// apply scene to window
                }
            }
        };
        //set up button functionality
        one.setOnAction(eventHandler);
        two.setOnAction(eventHandler);
        three.setOnAction(eventHandler);
        //add to pane
        playerSelectPane.getChildren().addAll(title,one,two,three);
        //set up screen
        window.setScene(playerSelect);

    }

}
