package game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;


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
        // set window title
        primaryStage.setTitle(WINDOW_TITLE);
        // disallow resizing window
        primaryStage.setResizable(false);
        // apply scene to window
        primaryStage.setScene(scene);
        // backend ready
        backend();
        // show window
        primaryStage.show();
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                primaryStage
//            }
//        };
//        Thread t = new Thread(r, "x");
        /*PauseTransition delay = new PauseTransition(Duration.seconds(TRAINING_TIME));
        delay.setOnFinished(event -> {
            primaryStage.close();
            endGame();
            Platform.runLater(() -> {
                new Main().start(new Stage());
            });
        });
        delay.play();*/
    }

    @Override
    public void stop() {
        endGame();
    }

    /**
     * mechanics
     */
    public static void backend() {
        // initialize field
        addMudPuddles();
        addPlayerTanks();
        addAITanks();
        addBushes();
        // add listeners
        addListeners();
        // start game
        startGame();
    }

    /**
     * frame logic
     */
    public static void executeFrame() {
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
                    executeFrame();
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
        AI.writeElite();
        Object.global.clear();
        pane.getChildren().clear();
    }

    /**
     * add player tanks
     */
    public static void addPlayerTanks() {
        player = new ObjectBuilder[]{
                // player 1
                new Player().setImageView(new ImageView(new Image("red_player.png")))
                        .setPane(pane).addImageViewToPane()
                        .setRotate(new Rotate()).addRotateToImageView()
                        .setTranslate(new Translate()).addTranslateToImageView()
                        .setTranslateToRandomNonOverlappingPosition(),
                // player 2
                new Player().setImageView(new ImageView(new Image("green_player.png")))
                        .setPane(pane).addImageViewToPane()
                        .setRotate(new Rotate()).addRotateToImageView()
                        .setTranslate(new Translate()).addTranslateToImageView()
                        .setTranslateToRandomNonOverlappingPosition(),
                // player 3
                new Player().setImageView(new ImageView(new Image("blue_player.png")))
                        .setPane(pane).addImageViewToPane()
                        .setRotate(new Rotate()).addRotateToImageView()
                        .setTranslate(new Translate()).addTranslateToImageView()
                        .setTranslateToRandomNonOverlappingPosition()
        };
        for (Object object : player) {
            object.activate();
        }
    }

    /**
     * add AI tanks
     */
    public static void addAITanks() {
        int ai_count = AI_COUNT;
        /*if (new File(CACHE_PATH).isFile()) {
            ai_count -= ELITE_COUNT;
            AI.makeElite(pane);
        }*/
        for (int i = 0; i < ai_count; i++) {
            new AI().setImageView(new ImageView(new Image("black_player.png")))
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
        for (int i = 0; i < BUSH_COUNT-5; i++) {
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
        for (int i = 0; i < MUD_COUNT-3; i++) {
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

        scene.setOnMousePressed(event-> {

            if (Math.abs( (180/Math.PI) * Math.atan( (event.getY()-((Tank)player[2]).translate.getY())/(event.getX()-((Tank)player[2]).translate.getX()) )%360 - (((Tank)player[2]).rotate.getAngle())%360) < 10) {
                ((Tank)player[2]).east = false;
                ((Tank)player[2]).west = false;
            }
            else if (Math.abs( (180/Math.PI) * Math.atan( (event.getY()-((Tank)player[2]).translate.getY())/(event.getX()-((Tank)player[2]).translate.getX()) )%360 - (((Tank)player[2]).rotate.getAngle())%360) > 180) {
                ((Tank)player[2]).east = false;
                ((Tank)player[2]).west = true;
            }

            else if (Math.abs( (180/Math.PI) * Math.atan( (event.getY()-((Tank)player[2]).translate.getY())/(event.getX()-((Tank)player[2]).translate.getX()) )%360 - (((Tank)player[2]).rotate.getAngle())%360) < 180) {
                ((Tank)player[2]).west = false;
                ((Tank)player[2]).east = true;
            }

            if (event.getX() - ((Tank)player[2]).translate.getX() <= 3 && event.getY() - ((Tank)player[2]).translate.getY() <= 3) {
                ((Tank)player[2]).north = false;
            }
            else {
                ((Tank)player[2]).north = true;
            }
        });

        scene.setOnMouseReleased(
                event -> {
                    ((Tank)player[2]).north = false;
                    ((Tank)player[2]).east = false;
                    ((Tank)player[2]).west = false;
                });

        scene.setOnScroll( event -> {
            ((Tank)player[2]).fire = true;
        });

    }
}
