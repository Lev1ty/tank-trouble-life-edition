package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import neuralnet.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * computer tank
 *
 * @author adam
 */
public class AI extends Tank {
    /**
     * neural neuralNetwork
     */
    public NeuralNetwork neuralNetwork;

    /**
     * value constructor
     */
    public AI() {
        super();
        initializeNetwork();
    }

    /**
     * initialize neuralNetwork
     */
    private void initializeNetwork() {
        neuralNetwork = new NeuralNetwork();
        neuralNetwork.add(new InputLayer(INPUT_LAYER_SIZE))
                .add(new OutputLayer(OUTPUT_LAYER_SIZE))
                .randomizeLayers();
    }

    /**
     * compile variables to feed into neural network
     */
    private List<Double> collectInput() {
        List<Double> input = new ArrayList<>();
        input.add(translate.getX());
        input.add(translate.getY());
        for (Object object : global) {
            if (object instanceof AI && id != object.id) {
                if (!object.dead) {
                    input.add(object.translate.getX() - translate.getX());
                    input.add(object.translate.getY() - translate.getY());
                } else {
                    input.add(0.0);
                    input.add(0.0);
                }
            }
        }
        return input;
    }

    @Override
    public void act() {
        super.act();
        // reset headings
        north = false;
        south = false;
        east = false;
        west = false;
        // autopilot
        List<Double> results = neuralNetwork.evaluate(collectInput());
        int first = OutputLayer.argmax(results);
        switch (first) {
            case 0:
                north = true;
                break;
            case 1:
                south = true;
                break;
            case 2:
                east = true;
                break;
            case 3:
                west = true;
                break;
            case 4:
                fire = true;
                break;
        }
        results.set(first, 0.0);
        int second = OutputLayer.argmax(results);
        switch (second) {
            case 0:
                north = true;
                break;
            case 1:
                south = true;
                break;
            case 2:
                east = true;
                break;
            case 3:
                west = true;
                break;
            case 4:
                fire = true;
                break;
        }
    }

    /**
     * write elite
     */
    public static void writeElite() {
        List<AI> copy = new ArrayList<>();
        for (Object object : global) {
            if (object instanceof AI) {
                copy.add((AI) object);
            }
        }
        AI[] elite = copy.toArray(new AI[0]);
        Arrays.sort(elite, (o1, o2) -> Double.compare(o2.score, o1.score));
        try (FileWriter fw = new FileWriter(CACHE_PATH, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (AI ai : Arrays.asList(elite).subList(0, ELITE_COUNT)) {
                System.out.print(ai.score + " ");
                out.print(ai.neuralNetwork);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read elite
     */
    public static void makeElite(Pane pane) {
        for (int i = 0; i < ELITE_COUNT; i++) {
            AI ai = (AI) new AI().setImageView(new ImageView(new Image("blue_player.png")))
                    .setPane(pane).addImageViewToPane()
                    .setRotate(new Rotate()).addRotateToImageView()
                    .setTranslate(new Translate()).addTranslateToImageView()
                    .setTranslateToRandomNonOverlappingPosition();
            ai.activate();
            for (Layer layer : ai.neuralNetwork.network) {
                layer.readWeightsBiasesFromFile(new File(CACHE_PATH));
            }
        }
    }
}
