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
        for (int i = 0; i < AI_COUNT; i++) {
            AI tank = new AI("black_player.png");
            Bucket.objects.add(tank);
            pane.getChildren().add(tank.imageView);
        }

        // add bushes
        for (int i = 0; i < 20; i++) {
            Object bush = new Bush("bush.png");
            Bucket.objects.add(bush);
            pane.getChildren().add(bush.imageView);
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
                    for (Object object : Bucket.objects)
                        if (object instanceof AI)
                            ((AI) object).plannedAction();
                    // temporary bullets container
                    List<Object> bullets = new ArrayList<>();
                    // temporary bullets avatar image container
                    List<ImageView> bulletImageViews = new ArrayList<>();

                    // tank loop
                    for (Object object : Bucket.objects) {
                        // only apply to tanks
                        if (object instanceof AI) {
                            // get alias
                            Tank tank = (Tank) object;
                            // initialize tank for frame updates
                            tank.initializeForFrame();
                            // act on heading
                            if (tank.north) {
                                tank.goNorth();
                                tank.north = false;
                            }
                            if (tank.south) {
                                tank.goSouth();
                                tank.south = false;
                            }
                            if (tank.east) {
                                tank.goEast();
                                tank.east = false;
                            }
                            if (tank.west) {
                                tank.goWest();
                                tank.west = false;
                            }
                            // act on fire
                            if (tank.bullets >= 5) tank.fire = false;
                            if (tank.fire) {
                                // initialize new bullet
                                Bullet bullet = new Bullet(tank, "bullet.png");
                                // add bullet to temporary array
                                bullets.add(bullet);
                                // add bullet avatar to temporary avatar image array
                                bulletImageViews.add(bullet.imageView);
                                // reset fire flag
                                tank.fire = false;
                            }
                            // check for blocking
                            for (Object object1 : Bucket.objects)
                                // if blocked
                                if (((Tank) object).block(object1))
                                    // act accordingly
                                    ((Tank) object).blocked();
                        }
                        // only apply to tanks
                        if (object instanceof Tank) {
                            // get alias
                            Tank tank = (Tank) object;
                            // initialize tank for frame updates
                            tank.initializeForFrame();
                            // act on heading
                            if (tank.north) tank.goNorth();
                            if (tank.south) tank.goSouth();
                            if (tank.east) tank.goEast();
                            if (tank.west) tank.goWest();
                            // act on fire
                            if (tank.bullets >= 5) tank.fire = false;
                            if (tank.fire) {
                                // initialize new bullet
                                Bullet bullet = new Bullet(tank, "bullet.png");
                                // add bullet to temporary array
                                bullets.add(bullet);
                                // add bullet avatar to temporary avatar image array
                                bulletImageViews.add(bullet.imageView);
                                // reset fire flag
                                tank.fire = false;
                            }
                            // check for blocking
                            for (Object object1 : Bucket.objects)
                                // if blocked
                                if (((Tank) object).block(object1))
                                    // act accordingly
                                    ((Tank) object).blocked();
                        }
                    }

                    // add temporary bullets to global array
                    Bucket.objects.addAll(bullets);
                    // add temporary bullet avatar images to screen
                    pane.getChildren().addAll(bulletImageViews);

                    // killed
                    List<Object> toBeRemoved = new ArrayList<>();
                    // killed avatar images
                    List<ImageView> toBeRemovedImageViews = new ArrayList<>();

                    // bullet loop
                    for (Object object : Bucket.objects) {
                        // apply on to bullets
                        if (object instanceof Bullet) {
                            if ((System.nanoTime() - ((Bullet) object).time) > 5000000000L) {
                                // add bullet to killed array
                                toBeRemoved.add(object);
                                // add bullet avatar image to killed image array
                                toBeRemovedImageViews.add(object.imageView);
                                if (((Bullet) object).owner != null)
                                    ((Bullet) object).owner.bullets--;
                                continue;
                            }
                            // prepare bullet for frame updates
                            ((Bullet) object).initializeForFrame();
                            // propagate bullet forward
                            ((Bullet) object).goNorth();
                            // check collision
                            for (Object object1 : Bucket.objects) {
                                // check for kills and act accordingly
                                if (object1 instanceof Tank && ((Bullet) object).kill(object1)) {
                                    ((Bullet) object).owner.bullets--;
                                    ((Bullet) object).owner.kills++;
                                    // dematerialize bullet and victim tank
                                    // add bullet to killed array
                                    toBeRemoved.add(object);
                                    // add bullet avatar image to killed image array
                                    toBeRemovedImageViews.add(object.imageView);
                                    // add victim tank to killed array
                                    toBeRemoved.add(object1);
                                    // add victim tank avatar image to killed image array
                                    toBeRemovedImageViews.add(object1.imageView);
                                }
                                // check for bushes and act accordingly
                                if ((object1 instanceof Bush) && ((Bullet) object).bounce(object1))
                                    ((Bullet) object).bounced(object1);
                            }
                        }
                    }

                    // remove killed from global array
                    Bucket.objects.removeAll(toBeRemoved);
                    // remove killed avatar images from screen
                    pane.getChildren().removeAll(toBeRemovedImageViews);

                    // player killed
                    // remove player from player array and action listener
                    // if player array is not empty, then global array cannot be empty
//                    if (!player.isEmpty() && player.get(0).id != Bucket.objects.get(0).id)
//                        player.remove(0);
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
