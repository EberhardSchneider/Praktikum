package Algorithm;

import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

/**
 * This should be implemented by all classes, which process Image informations.
 * Every Algorithm is expected to delive a NEW buffered image.
 */
public interface iAlgorithm {
    BufferedImage processImage(BufferedImage image);
}
