package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application implements Constants {
    /**
     * player tanks
     */
    private ObjectBuilder[] player;

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
        // initialize field
        addMudPuddles();
        addPlayerTanks();
        addAITanks();
        addBushes();
        // add listeners
        addListeners();
        // start game
        startGame();
        // show window
        primaryStage.show();
    }

    /**
     * game main loop
     */
    public void startGame() {
        // initialize animation framework
        Timeline timeline = new Timeline();
        // infinite game loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        // game loop
        KeyFrame keyFrame = new KeyFrame(
                // 120 Hz refresh rate
                Duration.seconds(1 / REFRESH_RATE),
                event -> {
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
        );
        // apply game loop to animator
        timeline.getKeyFrames().add(keyFrame);
        // start animator
        timeline.play();
    }

    /**
     * add player tanks
     */
    private void addPlayerTanks() {
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
                        .setTranslateToRandomNonOverlappingPosition()
        };
        for (Object object : player) {
            object.activate();
        }
    }

    /**
     * add AI tanks
     */
    private void addAITanks() {
        for (int i = 0; i < AI_COUNT; i++) {
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
    private void addBushes() {
        for (int i = 0; i < BUSH_COUNT; i++) {
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
    private void addMudPuddles() {
        for (int i = 0; i < MUD_COUNT; i++) {
            new Mud().setImageView(new ImageView(new Image("mud.png")))
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
    private void addListeners() {
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
}
