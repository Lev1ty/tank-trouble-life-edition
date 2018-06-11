package neuralnet;

import java.util.List;

public class InputLayer extends DotPlusLayer {
    /**
     * value constructor
     *
     * @param neurons number of neurons in this perceptron
     */
    public InputLayer(int neurons) {
        super(neurons);
    }

    @Override
    public void add() {
        network.add(this);
    }

    /**
     * feed input to network
     */
    public void feed(List<Double> input) {
        this.state = input;
    }
}
