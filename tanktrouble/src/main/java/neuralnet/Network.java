package neuralnet;

import java.util.ArrayList;
import java.util.List;

/**
 * neural network
 *
 * @author adam
 */
public class Network {
    /**
     * network
     */
    public List<Perceptron> network;

    /**
     * default constructor
     */
    public Network() {
        network = new ArrayList<>();
    }
}
