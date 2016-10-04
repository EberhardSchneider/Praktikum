package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 04.10.2016.
 */
public class ConvertDistanceMatrixToImage implements iImageAlgorithm {
    int[][] array;

    public ConvertDistanceMatrixToImage(int[][] array) {
        this.array = array;
    }

    @Override
    public BufferedImage processImage(BufferedImage image) {

        int height = array.length;
        int width = array[0].length;

        BufferedImage result = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);

        // get maximum in array
        int max = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ( array[y][x] > max )
                    max = array[y][x];
            }
        }

        // set colors of resulting image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int c = 255 - (array[y][x] * 255/max);
                int rgb = c << 16 | c << 8 | c;

                result.setRGB( x, y, rgb );
            }
        }


        return result;
    }
}
