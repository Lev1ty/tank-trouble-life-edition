package neuralnet;

import game.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * perceptron
 *
 * @author adam
 */
public abstract class Perceptron implements Constants {
    /**
     * network
     */
    public static List<Perceptron> network;
    /**
     * number of neurons in perceptron
     */
    protected int neurons;
    /**
     * state of neurons in perceptron
     */
    protected List<Double> state;
    /**
     * previous perceptron and next perceptron
     */
    protected Perceptron previous, next;

    /**
     * value constructor
     *
     * @param neurons number of neurons in this perceptron
     */
    public Perceptron(int neurons, ArrayList<Double> state) {
        this.neurons = neurons;
        this.state = state;
        network.add(this);
    }

    /**
     * calculate next state based on this state
     */
    public abstract void propagateForward();
}
