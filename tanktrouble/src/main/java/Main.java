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
        primaryStage.setTitle(Constants.WINDOW_TITLE);
        // disallow resizing window
        primaryStage.setResizable(false);
        // apply scene to window
        primaryStage.setScene(Constants.scene);
        // add player tanks
        new Tank(false).setImageView(new ImageView(new Image("red_player.png")))
                .setPane(Constants.pane).addImageViewToPane()
                .setRotate(new Rotate()).addRotateToImageView()
                .setTranslate(new Translate()).addTranslateToImageView()
                .setTranslateToRandomNonOverlappingPosition()
                .activate();
        new Tank(false).setImageView(new ImageView(new Image("green_player.png")))
                .setPane(Constants.pane).addImageViewToPane()
                .setRotate(new Rotate()).addRotateToImageView()
                .setTranslate(new Translate()).addTranslateToImageView()
                .setTranslateToRandomNonOverlappingPosition()
                .activate();
        // add AI tanks
        for (int i = 0; i < AI_COUNT; i++) {
            new Tank(true).setImageView(new ImageView(new Image("black_player.png")))
                    .setPane(Constants.pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition()
                    .activate();
        }
        // add bushes
        for (int i = 0; i < BUSH_COUNT; i++) {
            new Bush().setImageView(new ImageView(new Image("bush.png")))
                    .setPane(Constants.pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition()
                    .activate();
        }
        // key listeners
        Constants.scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    ((Tank) Object.global.get(0)).north = true;
                    break;
                case DOWN:
                    ((Tank) Object.global.get(0)).south = true;
                    break;
                case RIGHT:
                    ((Tank) Object.global.get(0)).east = true;
                    break;
                case LEFT:
                    ((Tank) Object.global.get(0)).west = true;
                    break;
                case M:
                    ((Tank) Object.global.get(0)).fire = true;
                    break;
                case W:
                    ((Tank) Object.global.get(1)).north = true;
                    break;
                case S:
                    ((Tank) Object.global.get(1)).south = true;
                    break;
                case D:
                    ((Tank) Object.global.get(1)).east = true;
                    break;
                case A:
                    ((Tank) Object.global.get(1)).west = true;
                    break;
                case Q:
                    ((Tank) Object.global.get(1)).fire = true;
                    break;
            }
        });
        Constants.scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    ((Tank) Object.global.get(0)).north = false;
                    break;
                case DOWN:
                    ((Tank) Object.global.get(0)).south = false;
                    break;
                case RIGHT:
                    ((Tank) Object.global.get(0)).east = false;
                    break;
                case LEFT:
                    ((Tank) Object.global.get(0)).west = false;
                    break;
                case W:
                    ((Tank) Object.global.get(1)).north = false;
                    break;
                case S:
                    ((Tank) Object.global.get(1)).south = false;
                    break;
                case D:
                    ((Tank) Object.global.get(1)).east = false;
                    break;
                case A:
                    ((Tank) Object.global.get(1)).west = false;
                    break;
            }
        });
        // initialize animation framework
        Timeline timeline = new Timeline();
        // infinite game loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        // game loop
        KeyFrame keyFrame = new KeyFrame(
                // 120 Hz refresh rate
                Duration.seconds(1.0 / 120),
                event -> {
                    // dump buffer
                    for (Object object : Object.buffer) {
                        object.activate();
                    }
                    // clear buffer
                    Object.buffer.clear();
                    // interact all objects with each other
                    for (Object object : Object.global) {
                        // self action
                        object.act();
                        for (Object object1 : Object.global) {
                            // interaction
                            object.interact(object1);
                        }
                    }
                }
        );
        // apply game loop to animator
        timeline.getKeyFrames().add(keyFrame);
        // start animator
        timeline.play();
        // show window
        primaryStage.show();
    }
}
