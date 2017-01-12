package GUI;

import Algorithm.iImageAlgorithm;
import Vector.VectorImage;
import StateAlgorithm.*;

import java.awt.image.BufferedImage;


/**
 * State Class.
 * Holds a reference to an instance of imageData and an algorithm to process it.
 *
 */
public class State {
    ImageData imageData;
    iStateAlgorithm algorithm;

    public State( BufferedImage i, int[][] iArray, VectorImage vImage) {
        ImageData iData = new ImageData( i, iArray, vImage);
        this.imageData = iData;
    }
}
