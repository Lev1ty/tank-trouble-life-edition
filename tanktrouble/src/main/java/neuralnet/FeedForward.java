package neuralnet;

import game.Constants;

import java.util.ArrayList;
import java.util.List;

public class FeedForward extends Perceptron {
    /**
     * weights
     */
    List<List<Double>> weights;

    /**
     * value constructor
     *
     * @param neurons number of neurons in this perceptron
     */
    public FeedForward(int neurons) {
        super(neurons, new ArrayList<>());
        if (!(this instanceof Input)) {
            previous = network.get(network.size() - 2);
            previous.next = this;
        }
    }

    /**
     * initialize weights
     *
     * @param low lower bound of weights
     * @param high higher bound of weights
     */
    public FeedForward setRandomWeights(int low, int high) {
        List<List<Double>> weights = new ArrayList<>();
        for (int i = 0; i < next.neurons; i++) {
            weights.add(new ArrayList<>());
            for (int j = 0; j < neurons; j++) {
                weights.get(i).add(Constants.randomRange(low, high));
            }
        }
        return setWeights(weights);
    }

    /**
     * validate and set weights
     *
     * @param weights template
     */
    public FeedForward setWeights(List<List<Double>> weights) {
        assert weights.size() == next.neurons;
        for (List<Double> list : weights) {
            assert list.size() == neurons;
        }
        this.weights = weights;
        return this;
    }

    @Override
    public void propagateForward() {
        for (int i = 0; i < next.neurons; i++) {
            double sum = 0;
            for (int j = 0; j < neurons; j++) {
                sum += state.get(j) * weights.get(i).get(j);
            }
            next.state.add(sum);
        }
    }
}
