import java.util.ArrayList;
import java.util.List;

public class AI extends Tank {
    public static List<AI> inputs = new ArrayList<>();
    public double[][][] weights;

    public AI(String imagePath) {
        super(imagePath);
        // zero score
        kills = 0;
        bullets = 0;
        weights = new double[5][AI_COUNT][2];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < AI_COUNT; j++)
                for (int k = 0; k < 2; k++)
                    weights[i][j][k] = Utility.random.nextDouble();
        inputs.add(this);
    }

    public void plannedAction() {
        double value = 0; int choice = 0;
        for (int i = 0; i < 5; i++) {
            double current = summarize(i);
            if (current > value) {
                choice = i;
                value = current;
            }
        }
        switch (choice) {
            case 0:
                fire = true;
                break;
            case 1:
                north = true;
                break;
            case 2:
                south = true;
                break;
            case 3:
                east = true;
                break;
            case 4:
                west = true;
        }
    }

    public double summarize(int index) {
        double sum = 0;
        for (int i = 0; i < AI_COUNT; i++) {
            AI a = inputs.get(i);
            sum += a.rotate.getAngle() * weights[index][i][0] +
                    Math.sqrt(Math.pow(a.translate.getX()-translate.getX(), 2)+Math.pow(a.translate.getY()-translate.getY(), 2)) *weights[index][i][1];
        }
        return sum;
    }

    public void randomAction() {
        int weight = Utility.randomRange(0, 100);
        if (weight == 1) fire = true;
        else if (weight < 30) north = true;
        else if (weight < 40) south = true;
        else if (weight < 70) east = true;
        else if (weight < 90) west = true;
        else if (weight <= 100) north = true;
    }
}
