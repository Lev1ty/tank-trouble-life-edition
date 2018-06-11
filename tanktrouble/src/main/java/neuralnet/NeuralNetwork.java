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
    private void releaseLock() {
        Layer.network = null;
    }

    /**
     * add layer
     */
    public NeuralNetwork add(Layer layer) {
        lock();
        layer.add();
        releaseLock();
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
        releaseLock();
        return ((OutputLayer) network.get(network.size() - 1)).getOutput();
    }
}
