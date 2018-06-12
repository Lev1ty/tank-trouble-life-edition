package neuralnet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OutputLayer extends Layer {
    /**
     * value constructor
     *
     * @param neurons number of neurons in this perceptron
     */
    public OutputLayer(int neurons) {
        super(neurons, new ArrayList<>());
    }

    /**
     * argmax
     */
    public static int argmax(List<Double> list) {
        int index = 0;
        Double value = Double.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > value) {
                value = list.get(i);
                index = i;
            }
        }
        return index;
    }

    @Override
    public void propagateForward() {
        // do nothing
    }

    @Override
    public void add() {
        super.add();
        previous = network.get(network.size() - 2);
        previous.next = this;
    }

    @Override
    public void readWeightsBiasesFromFile(File file) {
        // do nothing
    }

    /**
     * get results
     */
    public List<Double> getOutput() {
        return state;
    }
}
