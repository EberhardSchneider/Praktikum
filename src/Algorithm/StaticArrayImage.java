package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 22.10.2016.
 */
public class StaticArrayImage {
    static BufferedImage showArrayInImage(int[][] array, Color c) {
        int width = array.length;
        int height = array[0].length;

        BufferedImage resultingImage = new BufferedImage( width, height, BufferedImage.TYPE_4BYTE_ABGR);

        int rgb = 0xFF000000 | c.getRGB();

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (array[x][y] > 0)
                    resultingImage.setRGB( x, y, rgb);
            }

        return resultingImage;
    }

    static BufferedImage addArrayToImage(BufferedImage image, int[][] array, Color c) {
        int width = array.length;
        int height = array[0].length;

        int rgb = c.getRGB();

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (array[x][y] > 0)
                    image.setRGB( x, y, rgb);
            }

        return image;

    }
}
