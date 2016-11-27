package Algorithm;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Converts a grayscale BufferedImage to an array of 0s and 1s
 * with size width x height of the image.
 * If the brightness of a pixel is greater than threshold the resulting array
 * contains a 1.
 */
public class ConvertImageToArray implements iArrayAlgorithm {

    BufferedImage image;
    int threshold;

    /**
     * Constructor of a ConvertToImage Algorithm
     * @param image BufferedImage that  is converted.
     * @param threshold If a pixel is brighter than threshold the resulting array
     *                  contains a 1.
     */
    public ConvertImageToArray(BufferedImage image, int threshold) {
        this.image = image;
        this.threshold = threshold;
    }
    public int [][] processArray(int[][] array) {

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[width][height];


        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int argb = image.getRGB( j, i);
                int alpha = (argb >> 24 ) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = (argb ) & 0xFF;
                int grayValue = (int)(red * 0.299 + green * 0.587 + blue * 0.114);


                if ( grayValue < threshold) {
                    result[j][i] = 1;
                } else
                    result[j][i] = 0;
            }

        return result;
    }
}
