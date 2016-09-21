package GUI;

import javafx.scene.image.Image;

/**
 * State Class.
 * Holds a reference to an image and an algorithm to process it.
 *
 *
 */
public class State {
    Image image;
    Algorithm algorithm;

    public State( Image i, Algorithm a) {
        image = i;
        algorithm = a;
    }
}
