package neuralnet;

import java.util.ArrayList;
import java.util.List;

/**
 * neural network
 *
 * @author adam
 */
public class NeuralNetwork {
    /**
     * network
     */
    public List<Layer> network;

    /**
     * default constructor
     */
    public NeuralNetwork() {
        network = new ArrayList<>();
    }

    /**
     * lock in network reference
     */
    private void lock() {
        Layer.network = network;
    }

    /**
     * release network reference
     */
    private void unlock() {
        Layer.network = null;
    }

    /**
     * add layer
     */
    public NeuralNetwork add(Layer layer) {
        lock();
        layer.add();
        unlock();
        return this;
    }

    /**
     * evaluate network
     */
    public List<Double> evaluate(List<Double> input) {
        lock();
        assert network.get(0) instanceof InputLayer;
        assert network.get(network.size() - 1) instanceof OutputLayer;
        ((InputLayer) network.get(0)).feed(input);
        for (Layer layer : network) {
            layer.propagateForward();
        }
        unlock();
        return ((OutputLayer) network.get(network.size() - 1)).getOutput();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Layer layer : network) {
            stringBuilder.append(layer);
        }
        return stringBuilder.toString();
    }

    /**
     * randomize layers weights
     */
    public void randomizeLayers() {
        lock();
        for (Layer layer : network) {
            if (layer instanceof DotPlusLayer) {
                ((DotPlusLayer) layer).setRandomWeightsBiases(-1, 1);
            }
        }
        unlock();
    }
}
