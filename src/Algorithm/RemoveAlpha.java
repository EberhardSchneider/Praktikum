package Algorithm;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

/**
 * Created by eberh_000 on 24.09.2016.
 */
public class RemoveAlpha implements iAlgorithm {
    public BufferedImage processImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, image.getType() );


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = image.getRGB( x, y);
                int a = (argb >> 24 ) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = (argb ) & 0xFF;
                double ratio = (double)a / 255.0;
                a = 255;
                r = (int)(ratio * r + ((1 - ratio ) * 255));
                g = (int)( ratio * g + ((1 - ratio ) * 255));
                b = (int)( ratio * b + ((1 - ratio ) * 255));
                int res = (a << 24) | ( r << 16) | (g << 8) | b;

                result.setRGB(x, y, res);
            }
        }

        return result;
    }
}
