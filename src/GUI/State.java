package GUI;

import Algorithm.iImageAlgorithm;

import java.awt.image.BufferedImage;


/**
 * State Class.
 * Holds a reference to an image and an algorithm to process it.
 *
 *
 */
public class State {
    BufferedImage image;
    iImageAlgorithm algorithm;

    public State( BufferedImage i, iImageAlgorithm a) {
        image = i;
        algorithm = a;
    }
}
