package Algorithm;

import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

/**
 * This schould be implemented by all classes, which process Image informations.
 * Every Algorithm returns a NEW processed image.
 */
public abstract class Algorithm {
    protected BufferedImage image;

    // Setter Method for the image field
    public void setImage(BufferedImage i) {
        image = i;
    }

    // Public Method to run the algorithm and get the processed Image.
    public BufferedImage getProcessedImage() {
        BufferedImage processedImage = processImage();
        return processedImage;
    }

    abstract BufferedImage processImage();
}
