import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public abstract class Object implements ApplicationParameters {
    public static int idCount = 0;
    public int id;
    public String imagePath;
    public ImageView imageView;
    public Set<Integer> friendlies;

    public Object(String imagePath) {
        id = idCount;
        idCount++;
        this.imagePath = imagePath;
        imageView = new ImageView(new Image(imagePath));
        friendlies = new HashSet<>();
        becomeFriends(this);
    }

    public double distanceTo(Object other, int x, int y) {
        return Math.sqrt(Math.pow(((DynamicObject) other).translate.getX() + other.imageView.getImage().getWidth() / 2 - x, 2) +
                Math.pow(((DynamicObject) other).translate.getY() + other.imageView.getImage().getHeight() / 2 - y, 2)) -
                other.imageView.getImage().getWidth() / 2 - imageView.getImage().getWidth() / 2;
    }

    public double distanceTo(Object other) {
        return distanceTo(other, (int) (((DynamicObject) this).translate.getX() + imageView.getImage().getWidth() / 2),
                (int) (((DynamicObject) this).translate.getY() + imageView.getImage().getHeight() / 2));
    }

    public boolean kill(Object other) {
        for (Integer integer : friendlies)
            if (other.id == integer)
                return false;
        for (Integer integer : other.friendlies)
            if (id == integer)
                return false;
        return hit(other);
    }

    public boolean hit(Object other) {
        return distanceTo(other) <= 0;
    }

    public void becomeFriends(Object other) {
        friendlies.add(other.id);
        other.friendlies.add(id);
    }

    public int[] generateRandomPosition() {
        int x = Utility.randomRange((int) imageView.getImage().getWidth() / 2,
                WIDTH - (int) imageView.getImage().getWidth() / 2);
        int y = Utility.randomRange((int) imageView.getImage().getHeight() / 2,
                HEIGHT - (int) imageView.getImage().getHeight() / 2);
        while (true) {
            boolean flag = false;
            for (Object object : Bucket.objects) {
                if (distanceTo(object, x, y) < 0) {
                    x = Utility.randomRange((int) imageView.getImage().getWidth() / 2,
                            WIDTH - (int) imageView.getImage().getWidth() / 2);
                    y = Utility.randomRange((int) imageView.getImage().getHeight() / 2,
                            HEIGHT - (int) imageView.getImage().getHeight() / 2);
                    flag = true;
                    break;
                }
            }
            if (!flag) break;
        }
        return new int[]{(int) (x - imageView.getImage().getWidth() / 2),
                (int) (y - imageView.getImage().getHeight() / 2)};
    }
}
