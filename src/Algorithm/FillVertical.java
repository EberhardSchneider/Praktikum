

package Algorithm;

        import java.awt.*;
        import java.awt.image.BufferedImage;
        import Vector.*;

/**
 * Created by eberh_000 on 24.09.2016.
 */
public class FillVertical implements iImageAlgorithm {

    int numberOfGraylevels;
    VectorImage resultingVectorImage = new VectorImage();

    int scale = 30;

    public FillVertical(int numberOfGraylevels) {
        this.numberOfGraylevels = numberOfGraylevels;
    }
    public BufferedImage processImage(BufferedImage image) {


        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        BufferedImage result = new BufferedImage( oldWidth * 30, oldHeight*30, image.getType() );

        Graphics2D g = result.createGraphics();

        int numberOfPoints = oldWidth * oldHeight;

        int[] xPoints = new int[numberOfPoints];
        int[] yPoints = new int[numberOfPoints];
        int[] brightness = new int[numberOfPoints];

        int index = 0;

        for (int x = 0; x < oldWidth; x++) {
            for (int y = 0; y < oldHeight; y++) {
                int xPos = x * 30;
                int yPos = ( (x%2) == 0) ? y * 30 : oldHeight*30 - y * 30;

                xPoints[index] = xPos;
                yPoints[index] = yPos;

                int r = ( image.getRGB(x, (x%2)==0 ? y : oldHeight-y-1) >> 16) & 0xFF;
                brightness[index] = (int)( ((float)r / 256f) * numberOfGraylevels );


                index++;
            }
        }

        BasicStroke[] stroke = new BasicStroke[numberOfGraylevels];
        for (int i = 0; i<numberOfGraylevels; i++) {
            stroke[i] = new BasicStroke(i+2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
        }

        g.setColor( Color.WHITE );
        for (int i = 1; i < numberOfPoints; i++) {
            g.setStroke( stroke[ brightness[i] ] );
            g.drawLine( xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i]);
            resultingVectorImage.addLine( xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i], numberOfGraylevels-brightness[i] );
        }


        return result;
    }

    public VectorImage getSVG() {
        return resultingVectorImage;
    }
}
