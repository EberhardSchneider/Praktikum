package Algorithm;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * Algorithm to get images with low resolution to get the linefill process working.
 * This comes from: http://www.mkyong.com/java/how-to-resize-an-image-in-java/
 */
public class ScaleDown implements iImageAlgorithm {

    int newWidth = 100;
    int newHeight = 100;

    public ScaleDown( int w, int h ) {
        newWidth = w;
        newHeight = h;
    }

    public BufferedImage processImage(BufferedImage image) {

        int imageType = image.getType();

        BufferedImage result = new BufferedImage( newWidth, newHeight, imageType );
        Graphics2D g = result.createGraphics();
        g.drawImage( image, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return result;
    }

    public void setParam( int width, int height) {
        newWidth = width;
        newHeight = height;
    }
}
