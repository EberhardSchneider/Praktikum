package GUI;

import javafx.scene.image.Image;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;

/**
 * State Class.
 * Holds a reference to an image and an algorithm to process it.
 *
 *
 */
public class State {
    BufferedImage image;
    Algorithm algorithm;

    public State( BufferedImage i, Algorithm a) {
        image = i;
        algorithm = a;
    }
}
