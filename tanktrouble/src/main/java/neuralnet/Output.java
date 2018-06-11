package neuralnet;

import java.util.ArrayList;

public class Output extends Perceptron {
    /**
     * value constructor
     *
     * @param neurons  number of neurons in this perceptron
     */
    public Output(int neurons) {
        super(neurons, new ArrayList<>());
        previous = network.get(network.size() - 2);
    }

    @Override
    public void propagateForward() {
        // do nothing
    }
}
