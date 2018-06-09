import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI
 *
 * @author adam
 */
public class Main extends Application implements ApplicationParameters {

    /**
     * entry point for application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start game
     *
     * @param primaryStage main window
     */
    @Override
    public void start(Stage primaryStage) {
        // set title of window
        primaryStage.setTitle(TITLE);
        // make window fixed size
        primaryStage.setResizable(false);
        // apply scene to window
        primaryStage.setScene(scene);

        // create human player array and add players
        ArrayList<Object> player = new ArrayList<>();
        player.add(new Tank("red_player.png"));
        player.add(new Tank("green_player.png"));

        // add human players to global array and their avatars to screen (pane)
        Bucket.objects.addAll(player);
        for (Object object : player)
            pane.getChildren().add(object.imageView);

        // add AI players
        for (int i = 0; i < 10; i++) {
            Tank tank = new Tank("black_player.png");
            Bucket.objects.add(tank);
            pane.getChildren().add(tank.imageView);
        }

        // key press key listener
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).north = true;
                    break;
                case DOWN:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).south = true;
                    break;
                case RIGHT:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).east = true;
                    break;
                case LEFT:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).west = true;
                    break;
                case M:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).fire = true;
                    break;
                case W:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).north = true;
                    break;
                case S:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).south = true;
                    break;
                case D:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).east = true;
                    break;
                case A:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).west = true;
                    break;
                case Q:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).fire = true;
                    break;
            }
        });

        // key release key listener
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).north = false;
                    break;
                case DOWN:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).south = false;
                    break;
                case RIGHT:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).east = false;
                    break;
                case LEFT:
                    if (!player.isEmpty())
                        ((Tank) player.get(0)).west = false;
                    break;
                case W:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).north = false;
                    break;
                case S:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).south = false;
                    break;
                case D:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).east = false;
                    break;
                case A:
                    if (player.size() > 1)
                        ((Tank) player.get(1)).west = false;
                    break;
            }
        });

        // initialize animation framework
        Timeline timeline = new Timeline();
        // uninterrupted game loop
        timeline.setCycleCount(Timeline.INDEFINITE);
        // initialize game loop
        KeyFrame keyFrame = new KeyFrame(
                // 60 Hz refresh
                Duration.seconds(0.017),
                // game loop
                event -> {
                    List<Object> bullets = new ArrayList<>();
                    List<ImageView> bulletImageViews = new ArrayList<>();
                    for (Object object : Bucket.objects) {
                        if (object instanceof Tank) {
                            Tank tank = (Tank) object;
                            tank.initializeForFrame();
                            if (tank.north) tank.goNorth();
                            if (tank.south) tank.goSouth();
                            if (tank.east) tank.goEast();
                            if (tank.west) tank.goWest();
                            if (tank.fire) {
                                Bullet bullet = new Bullet(tank, "bullet.png");
                                bullets.add(bullet);
                                bulletImageViews.add(bullet.imageView);
                                tank.fire = false;
                            }
                            for (Object object1 : Bucket.objects) {
                                if (object.id != object1.id && !(object1 instanceof Bullet) && object.hit(object1))
                                    ((Tank) object).reverseNow();
                            }
                        }
                    }
                    pane.getChildren().addAll(bulletImageViews);
                    Bucket.objects.addAll(bullets);
                    List<Object> toBeRemoved = new ArrayList<>();
                    List<ImageView> toBeRemovedImageViews = new ArrayList<>();
                    for (Object object : Bucket.objects) {
                        if (object instanceof Bullet) {
                            ((Bullet) object).initializeForFrame();
                            ((Bullet) object).goNorth();
                            for (Object object1 : Bucket.objects)
                                if (object1 instanceof Tank && object.kill(object1)) {
                                    toBeRemoved.add(object);
                                    toBeRemovedImageViews.add(object.imageView);
                                    toBeRemoved.add(object1);
                                    toBeRemovedImageViews.add(object1.imageView);
                                    ((Tank) Bucket.objects.get(((Bullet) object).ownerId)).kills++;
                                }
                        }
                    }
                    Bucket.objects.removeAll(toBeRemoved);
                    pane.getChildren().removeAll(toBeRemovedImageViews);
                    if (!player.isEmpty() && player.get(0).id != Bucket.objects.get(0).id)
                        player.remove(0);
                }
        );
        // apply game loop to animator
        timeline.getKeyFrames().add(keyFrame);
        // start animator
        timeline.play();

        // open window
        primaryStage.show();
    }
}
