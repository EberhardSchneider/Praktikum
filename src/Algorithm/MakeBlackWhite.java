package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 02.10.2016.
 */
public class MakeBlackWhite implements iAlgorithm {

    public MakeBlackWhite( int threshold ) {
        this.threshold = threshold;
    }

    private int scale = 1;
    private int threshold = 190;

    public BufferedImage processImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage( scale * width, scale * height, image.getType() );

        Graphics2D g = result.createGraphics();

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int greenValue =  ( image.getRGB( x/scale, y/scale) >> 8 ) & 0xFF;
                int grayValue = (int)(greenValue * (1 / 0.587));

                if ( grayValue > threshold) {
                    result.setRGB( x, y, 0xFFFFFF);
                } else
                    result.setRGB( x,y, 0 );
            }
        return result;
    }
}
