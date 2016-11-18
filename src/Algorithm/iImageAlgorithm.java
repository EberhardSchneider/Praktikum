package Algorithm;

import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

/**
 * This should be implemented by all classes, which process Image informations.
 * Every Algorithm is expected to deliver a NEW buffered image.
 */
public interface iImageAlgorithm {
    BufferedImage processImage(BufferedImage image);


}
