package neuralnet;

import game.Constants;

import java.util.ArrayList;
import java.util.List;

public class DotPlusLayer extends Layer {
    /**
     * weights
     */
    List<List<Double[]>> weights;

    /**
     * value constructor
     *
     * @param neurons number of neurons in this perceptron
     */
    public DotPlusLayer(int neurons) {
        super(neurons, new ArrayList<>());
    }

    /**
     * initialize weights
     *
     * @param low  lower bound of weights
     * @param high higher bound of weights
     */
    public void setRandomWeightsBiases(int low, int high) {
        List<List<Double[]>> weights = new ArrayList<>();
        for (int i = 0; i < next.neurons; i++) {
            weights.add(new ArrayList<>());
            for (int j = 0; j < neurons; j++) {
                weights.get(i).add(new Double[]{Constants.randomRange(low, high), Constants.randomRange(low, high)});
            }
        }
        setWeightsBiases(weights);
    }

    /**
     * validate and set weights
     *
     * @param weights template
     */
    public void setWeightsBiases(List<List<Double[]>> weights) {
        assert weights.size() == next.neurons;
        for (List<Double[]> list : weights) {
            assert list.size() == neurons;
        }
        this.weights = weights;
    }

    @Override
    public void propagateForward() {
        for (int i = 0; i < next.neurons; i++) {
            double sum = 0;
            for (int j = 0; j < neurons; j++) {
                sum += state.get(j) * weights.get(i).get(j)[0] + weights.get(i).get(j)[1];
            }
            next.state.add(sum);
        }
    }

    @Override
    public void add() {
        super.add();
        if (!(this instanceof InputLayer)) {
            previous = network.get(network.size() - 2);
            previous.next = this;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        for (List<Double[]> list : weights) {
            for (Double[] list1 : list) {
                for (Double d : list1) {
                    stringBuilder.append(d).append(" ");
                }
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
