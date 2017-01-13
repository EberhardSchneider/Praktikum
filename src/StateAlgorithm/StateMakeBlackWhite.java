package StateAlgorithm;

import GUI.ImageData;
import GUI.State;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 10.01.2017.
 */
public class StateMakeBlackWhite implements iStateAlgorithm {
    public StateMakeBlackWhite( int threshold ) {
        this.threshold = threshold;
    }

    private int scale = 1;
    private int threshold = 190;

    public State processImage(ImageData imageData) {
        BufferedImage image = imageData.getImage();
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage( scale * width, scale * height, BufferedImage.TYPE_BYTE_GRAY );

        Graphics2D g = result.createGraphics();

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int argb = image.getRGB( x / scale, y / scale);
                int alpha = (argb >> 24 ) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = (argb ) & 0xFF;
                int grayValue = (int)(red * 0.299 + green * 0.587 + blue * 0.114);



                if ( grayValue > threshold) {
                    result.setRGB( x, y, 0xFFFFFF);
                } else
                    result.setRGB( x,y, 0 );
            }
        return new State(result, imageData.getImageArray(), imageData.getVectorImage() );
    }
}
