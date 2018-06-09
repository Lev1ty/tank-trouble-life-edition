import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements ApplicationParameters {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);

        Bucket.objects.add(new Tank("red_player.png"));
        Bucket.objects.add(new Tank("green_player.png"));
//        Bucket.objects.add(new Tank("blue_player.png"));

        for (Object object : Bucket.objects)
            pane.getChildren().add(object.imageView);

        for (int i = 0; i < 10; i++) {
            Tank tank = new Tank("black_player.png");
            Bucket.objects.add(tank);
            pane.getChildren().add(tank.imageView);
        }

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    ((Tank) Bucket.objects.get(0)).north = true;
                    break;
                case DOWN:
                    ((Tank) Bucket.objects.get(0)).south = true;
                    break;
                case RIGHT:
                    ((Tank) Bucket.objects.get(0)).east = true;
                    break;
                case LEFT:
                    ((Tank) Bucket.objects.get(0)).west = true;
                    break;
                case M:
                    ((Tank) Bucket.objects.get(0)).fire = true;
                    break;
                case W:
                    ((Tank) Bucket.objects.get(1)).north = true;
                    break;
                case S:
                    ((Tank) Bucket.objects.get(1)).south = true;
                    break;
                case D:
                    ((Tank) Bucket.objects.get(1)).east = true;
                    break;
                case A:
                    ((Tank) Bucket.objects.get(1)).west = true;
                    break;
                case Q:
                    ((Tank) Bucket.objects.get(1)).fire = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    ((Tank) Bucket.objects.get(0)).north = false;
                    break;
                case DOWN:
                    ((Tank) Bucket.objects.get(0)).south = false;
                    break;
                case RIGHT:
                    ((Tank) Bucket.objects.get(0)).east = false;
                    break;
                case LEFT:
                    ((Tank) Bucket.objects.get(0)).west = false;
                    break;
                case W:
                    ((Tank) Bucket.objects.get(1)).north = false;
                    break;
                case S:
                    ((Tank) Bucket.objects.get(1)).south = false;
                    break;
                case D:
                    ((Tank) Bucket.objects.get(1)).east = false;
                    break;
                case A:
                    ((Tank) Bucket.objects.get(1)).west = false;
                    break;
            }
        });

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.017),
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
                }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        primaryStage.show();
    }
}
