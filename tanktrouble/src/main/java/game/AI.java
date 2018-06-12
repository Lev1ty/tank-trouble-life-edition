package game;

import neuralnet.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * computer tank
 *
 * @author adam
 */
public class AI extends Tank {
    /**
     * neural neuralNetwork
     */
    public NeuralNetwork neuralNetwork;

    /**
     * value constructor
     */
    public AI() {
        super();
        initializeNetwork();
    }

    /**
     * initialize neuralNetwork
     */
    private void initializeNetwork() {
        neuralNetwork = new NeuralNetwork();
        neuralNetwork.add(new InputLayer(INPUT_LAYER_SIZE))
                .add(new DotPlusLayer(INPUT_LAYER_SIZE))
                .add(new DotPlusLayer((int) Math.sqrt(INPUT_LAYER_SIZE)))
                .add(new OutputLayer(OUTPUT_LAYER_SIZE));
        for (Layer layer : neuralNetwork.network) {
            if (layer instanceof DotPlusLayer) {
                ((DotPlusLayer) layer).setRandomWeightsBiases(-1, 1);
            }
        }
    }

    /**
     * compile variables to feed into neural network
     */
    private List<Double> collectInput() {
        List<Double> input = new ArrayList<>();
        input.add(translate.getX());
        input.add(translate.getY());
        for (Object object : global) {
            if (object instanceof Tank && id != object.id) {
                input.add(object.translate.getX() - translate.getX());
                input.add(object.translate.getY() - translate.getY());
            }
        }
        return input;
    }

    @Override
    public void act() {
        super.act();
        // reset headings
        north = false;
        south = false;
        east = false;
        west = false;
        // autopilot
        switch (OutputLayer.argmax(neuralNetwork.evaluate(collectInput()))) {
            case 0:
                north = true;
                break;
            case 1:
                south = true;
                break;
            case 2:
                east = true;
                break;
            case 3:
                west = true;
                break;
            case 4:
                fire = true;
                break;
        }
    }

    /**
     * get elite
     */
    public static void saveElite() {
        AI[] copy = global.toArray(new AI[global.size()]);
        Arrays.sort(copy, (o1, o2) -> Double.compare(o2.score, o1.score));
        List<AI> elite = new ArrayList<>(Arrays.asList(copy)).subList(0, 15);
    }
}
