package game;

import neuralnet.Network;

/**
 * computer tank
 *
 * @author adam
 */
public class AI extends Tank {
    /**
     * neural network
     */
    private Network network;

    /**
     * value constructor
     *
     * @param network given neural network
     */
    public AI(Network network) {
        super();
        this.network = network;
    }
}
