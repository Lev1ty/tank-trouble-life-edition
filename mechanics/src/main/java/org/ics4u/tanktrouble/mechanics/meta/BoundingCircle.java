package org.ics4u.tanktrouble.mechanics.meta;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.ics4u.tanktrouble.mechanics.buckets.TankTroubleBucket;
import org.ics4u.tanktrouble.mechanics.meta.vectors.PositionVector;
import org.ics4u.tanktrouble.mechanics.meta.vectors.TankTroubleVector;

import static org.ics4u.tanktrouble.utilities.Utilities.randomRange;

/**
 * bounding circle
 *
 * @author adam
 */
public class BoundingCircle extends BoundingArea {
    /**
     * circle radius
     */
    protected double radius;

    /**
     * value constructor
     *
     * @param pane
     * @param radius
     */
    protected BoundingCircle(Pane pane, int radius, Paint color) {
        this.radius = radius;
        int x = randomRange(radius, 1200 - radius);
        int y = randomRange(radius, 800 - radius);
        while (true) {
            boolean flag = false;
            for (TankTroubleObject tankTroubleObject : TankTroubleBucket.objects)
                if (((BoundingCircle) tankTroubleObject).edgeEdgeDistance(new BoundingCircle(x, y, radius)) < 0)
                    flag = true;
            if (!flag) break;
            x = randomRange(radius, 1200 - radius);
            y = randomRange(radius, 800 - radius);
        }
        position = new PositionVector(x, y);
        pane.getChildren().add(new Circle(x, y , radius, color));
    }

    /**
     * utility value constructor
     *
     * @param x x-ocmponent position
     * @param y y-component position
     * @param radius radius of circle
     */
    private BoundingCircle(int x, int y, int radius) {
        this.radius = radius;
        position = new PositionVector(x, y);
    }

    @Override
    public double toEdgeDistance(TankTroubleVector direction) {
        return radius;
    }
}
