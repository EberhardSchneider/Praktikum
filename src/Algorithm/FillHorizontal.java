package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 24.09.2016.
 */
public class FillHorizontal implements iImageAlgorithm {

    int numberOfGraylevels;

    public FillHorizontal(int numberOfGraylevels) {
        this.numberOfGraylevels = numberOfGraylevels;
    }
    public BufferedImage processImage(BufferedImage image) {

        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        BufferedImage result = new BufferedImage( oldWidth * 15, oldHeight*15, image.getType() );

        Graphics2D g = result.createGraphics();

        int numberOfPoints = oldWidth * oldHeight;

        int[] xPoints = new int[numberOfPoints];
        int[] yPoints = new int[numberOfPoints];
        int[] brightness = new int[numberOfPoints];

        int index = 0;

        for (int y = 0; y < oldHeight; y++) {
            for (int x = 0; x < oldWidth; x++) {
                int yPos = y * 15;
                int xPos = ( (y%2) == 0) ? x * 15 : oldWidth*15 - x * 15;

                xPoints[index] = xPos;
                yPoints[index] = yPos;

                int r = ( image.getRGB( (y%2)==0 ? x : oldWidth-x-1, y) >> 16) & 0xFF;
                brightness[index] = (int)( ((float)r / 256f) * numberOfGraylevels );

                index++;
            }
        }

        BasicStroke[] stroke = new BasicStroke[numberOfGraylevels];
        for (int i = 0; i<numberOfGraylevels; i++)
            stroke[i] = new BasicStroke( numberOfGraylevels+2-i, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);

        g.setColor( Color.BLACK );
       for (int i = 1; i < numberOfPoints; i++) {
           g.setStroke( stroke[ brightness[i] ] );
            g.drawLine( xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i]);
        }


        return result;
    }
}
