package game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Handler;

import static game.Constants.WINDOW_TITLE;

public class Main extends Application implements DynamicConstants {
    /**
     * player tanks
     */
    private static ObjectBuilder[] player;

    /**
     *window
     */
    private static Stage window;

    /**
     *screen  object
     */
    private static Screens s;

    /**
     * number of players counter
     */
    private static int num = 0;

    private static int count = 0;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * main application body
     *
     * @param primaryStage window
     */
    @Override
    public void start(Stage primaryStage) {
        //initialize screens variable
        s = new Screens();
        //equate variables
        window = primaryStage;
        // set window title
        window.setTitle(WINDOW_TITLE);
        //create icon
        window.getIcons().add(new Image(("icon.png")));
        // disallow resizing window
        window.setResizable(false);
        //set up the menu screen
        runMenu();

        // show window
        window.show();

    }

    /**
     * stop the game
     */
    @Override
    public void stop() {
        endGame();
    }

    /**
     * method to set up back end mechanics
     * @param numPlayers
     */
    public static void backend(int numPlayers) {
        // initialize playing field
        addMudPuddles();//set up mud/sand
        addPlayerTanks(numPlayers);//add user tanks
        addAITanks();//set up AI
        addBushes();//set up obstacles

        // add listeners
        addListeners();
        // start game
        startGame();
    }

    /**
     * frame logic
     */
    public static void executeFrame(Timeline t) {
        //stores number of tanks alive
        int numAlive = 0;
        //determines whether an AI won or a user won
        boolean whoWon = false;
        //determines which user won
        Tank user = null;
        // dump buffer
        for (Object object : Object.buffer) {
            object.activate();
        }
        // clear buffer
        Object.buffer.clear();
        // actOn all objects with each other
        for (Object object : Object.global) {
            // object not dead
            if (!object.dead) {
                //update numAlive
                if(object instanceof Tank)//if object is a tank
                    numAlive++;
                if(numAlive == 1){
                    if(object instanceof Player) {//if the tank is a player
                        whoWon = true;//user has won
                        user = (Player)object;
                    }
                }

                // self action
                object.act();
                for (Object object1 : Object.global) {
                    // object1 not dead
                    if (!object1.dead) {
                        // interaction
                        object.actOn(object1);
                    }
                }
            }
        }
        if(numAlive == 0){//if no one dies
            t.stop();
            AlertBox.display("No one has survived.");//create new window displaying message
            endGame();
        }
        else if(numAlive == 1 && whoWon){//if one person survives and is user
            t.stop();//stop animation
            if(user.id == player[0].id) {//player 1 wins
                AlertBox.display("Player 1 has won!");//create new window
            }
            else if(user.id == player[1].id){//player 2 wins
                AlertBox.display("Player 2 has won!");//create new window
            }
            else{//player 3 wins
                AlertBox.display("Player 3 has won!");//create new window
            }
            endGame();
        }
        else if(numAlive == 1){//if one tank survives
            t.stop();
            AlertBox.display("The computer has won!");//create new window displaying message
            endGame();
        }
    }

    /**
     * game main loop
     */
    public static void startGame() {
        // initialize animation framework
        Timeline timeline = new Timeline();
        // infinite game loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        // game loop
        KeyFrame keyFrame = new KeyFrame(
                // 120 Hz refresh rate
                Duration.seconds(1 / REFRESH_RATE),
                event -> {
                    executeFrame(timeline);
                }
        );
        // apply game loop to animator
        timeline.getKeyFrames().add(keyFrame);
        // start animator
        timeline.play();
    }

    /**
     * end game
     */
    public static void endGame() {
//        AI.writeElite();
        Object.global.clear();//clear array of objects
        pane.getChildren().clear();//clear the screen
    }

    /**
     * add player tanks
     */
    public static void addPlayerTanks(int numPlayers) {
        num = numPlayers;
        player = new ObjectBuilder[numPlayers];//create list of player objects
        if(numPlayers >= 1) {// number of players exceeds 1
            // add player 1
            player[0] = new Player().setImageView(new ImageView(new Image("red_player.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition();
        }
        if(numPlayers >= 2) {//number of players exceeds 2
            // add player 2
            player[1] = new Player().setImageView(new ImageView(new Image("green_player.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition();
        }
        if(numPlayers >= 3){//number of players exceeds 3
            //add player 3
            player[2] = new Player().setImageView(new ImageView(new Image("blue_player.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition();
        }

        for (Object object : player) {//for every object which is a player
            object.activate();//activate
        }
    }

    /**
     * add AI tanks
     */
    public static void addAITanks() {
        int ai_count = AI_COUNT;//set number of AI

        for (int i = 0; i < ai_count; i++) {//for every AI
            //add AI tank
            new Laika().setImageView(new ImageView(new Image("black_player.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition()
                    .activate();
        }
    }

    /**
     * add bushes
     */
    public static void addBushes() {
        for (int i = 0; i < BUSH_COUNT; i++) {//for each bush
            //add bush object
            new Bush().setImageView(new ImageView(new Image("bush.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition()
                    .activate();
        }
    }

    /**
     * add mud puddles
     */
    public static void addMudPuddles() {
        for (int i = 0; i < MUD_COUNT; i++) {//for the number of mud puddles
            //add the mud object
            new Mud().setImageView(new ImageView(new Image("sand.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition()
                    .activate();
        }
    }

    /**
     * add listeners
     */
    public static void addListeners() {
        if(num >= 1) {//number of players exceeds 1
            // key press
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP:
                        ((Tank) player[0]).north = true;
                        break;
                    case DOWN:
                        ((Tank) player[0]).south = true;
                        break;
                    case RIGHT:
                        ((Tank) player[0]).east = true;
                        break;
                    case LEFT:
                        ((Tank) player[0]).west = true;
                        break;
                    case M:
                        ((Tank) player[0]).fire = true;
                        break;
                }
            });

            // key release
            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case UP:
                        ((Tank) player[0]).north = false;
                        break;
                    case DOWN:
                        ((Tank) player[0]).south = false;
                        break;
                    case RIGHT:
                        ((Tank) player[0]).east = false;
                        break;
                    case LEFT:
                        ((Tank) player[0]).west = false;
                        break;
                }
            });
        }
        if(num >= 2) {//number of players exceeds 2
            // key press
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP:
                        ((Tank) player[0]).north = true;
                        break;
                    case DOWN:
                        ((Tank) player[0]).south = true;
                        break;
                    case RIGHT:
                        ((Tank) player[0]).east = true;
                        break;
                    case LEFT:
                        ((Tank) player[0]).west = true;
                        break;
                    case M:
                        ((Tank) player[0]).fire = true;
                        break;
                    case W:
                        ((Tank) player[1]).north = true;
                        break;
                    case S:
                        ((Tank) player[1]).south = true;
                        break;
                    case D:
                        ((Tank) player[1]).east = true;
                        break;
                    case A:
                        ((Tank) player[1]).west = true;
                        break;
                    case Q:
                        ((Tank) player[1]).fire = true;
                        break;
                }
            });

            // key release
            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case UP:
                        ((Tank) player[0]).north = false;
                        break;
                    case DOWN:
                        ((Tank) player[0]).south = false;
                        break;
                    case RIGHT:
                        ((Tank) player[0]).east = false;
                        break;
                    case LEFT:
                        ((Tank) player[0]).west = false;
                        break;
                    case W:
                        ((Tank) player[1]).north = false;
                        break;
                    case S:
                        ((Tank) player[1]).south = false;
                        break;
                    case D:
                        ((Tank) player[1]).east = false;
                        break;
                    case A:
                        ((Tank) player[1]).west = false;
                        break;
                }
            });
        }
        if(num >= 3) {//number of players exceeds 3
            scene.setOnMousePressed(event -> {

                if (Math.abs((180 / Math.PI) * Math.atan((event.getY() - ((Tank) player[2]).translate.getY()) / (event.getX() - ((Tank) player[2]).translate.getX())) % 360 - (((Tank) player[2]).rotate.getAngle()) % 360) < 10) {
                    ((Tank) player[2]).east = false;
                    ((Tank) player[2]).west = false;
                } else if (Math.abs((180 / Math.PI) * Math.atan((event.getY() - ((Tank) player[2]).translate.getY()) / (event.getX() - ((Tank) player[2]).translate.getX())) % 360 - (((Tank) player[2]).rotate.getAngle()) % 360) > 180) {
                    ((Tank) player[2]).east = false;
                    ((Tank) player[2]).west = true;
                } else if (Math.abs((180 / Math.PI) * Math.atan((event.getY() - ((Tank) player[2]).translate.getY()) / (event.getX() - ((Tank) player[2]).translate.getX())) % 360 - (((Tank) player[2]).rotate.getAngle()) % 360) < 180) {
                    ((Tank) player[2]).west = false;
                    ((Tank) player[2]).east = true;
                }

                if (event.getX() - ((Tank) player[2]).translate.getX() <= 3 && event.getY() - ((Tank) player[2]).translate.getY() <= 3) {
                    ((Tank) player[2]).north = false;
                } else {
                    ((Tank) player[2]).north = true;
                }
            });

            scene.setOnMouseReleased(
                    event -> {
                        ((Tank) player[2]).north = false;
                        ((Tank) player[2]).east = false;
                        ((Tank) player[2]).west = false;
                    });

            scene.setOnScroll(event -> {
                ((Tank) player[2]).fire = true;
            });
        }
    }

    /**
     * menu method
     */
    public static void runMenu(){
        s.runMenu(window);//call the menu method in Screens class
    }

}
