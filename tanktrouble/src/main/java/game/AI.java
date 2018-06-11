package game;

import neuralnet.*;

import java.util.ArrayList;
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
    private NeuralNetwork neuralNetwork;

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
        neuralNetwork.add(new InputLayer((AI_COUNT + PLAYER_COUNT) * 2))
                .add(new DotPlusLayer((AI_COUNT + PLAYER_COUNT) * 2))
                .add(new DotPlusLayer((int) Math.sqrt((AI_COUNT + PLAYER_COUNT) * 2)))
                .add(new OutputLayer(5));
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
        for (Object object : global) {
            if (object instanceof Tank) {
                input.add(object.translate.getX());
                input.add(object.translate.getY());
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
            case 0: north = true; break;
            case 1: south = true; break;
            case 2: east = true; break;
            case 3: west = true; break;
            case 4: fire = true; break;
        }
    }
}
