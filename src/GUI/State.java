package GUI;

import Algorithm.iAlgorithm;

import java.awt.image.BufferedImage;


/**
 * State Class.
 * Holds a reference to an image and an algorithm to process it.
 *
 *
 */
public class State {
    BufferedImage image;
    iAlgorithm algorithm;

    public State( BufferedImage i, iAlgorithm a) {
        image = i;
        algorithm = a;
    }
}
