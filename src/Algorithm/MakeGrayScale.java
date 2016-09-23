package Algorithm;
import javafx.scene.image.*;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

import Algorithm.Algorithm;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

/**
 * This algorithm convverts an im age in grayscale.
 */
public class MakeGrayScale extends Algorithm {
    @Override
    BufferedImage processImage() {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, TYPE_BYTE_GRAY);


        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x,y);
                rgb = rgb & 0xFF;
                result.setRGB(x,y,rgb);
            }

        return result;
    }
}
