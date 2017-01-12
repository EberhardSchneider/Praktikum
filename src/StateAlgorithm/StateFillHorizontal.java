package StateAlgorithm;

import GUI.ImageData;
import GUI.State;
import Vector.VectorImage;

import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 28.11.2016.
 */
public class StateFillHorizontal implements iStateAlgorithm {

    int numberOfGraylevels = 20;

    public StateFillHorizontal(int numberOfGraylevels) {
        this.numberOfGraylevels = numberOfGraylevels;
    }

    @Override
    public State processImage(ImageData imageData) {
        BufferedImage image = imageData.getImage();

        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();

        VectorImage vectorImage = imageData.getVectorImage();

        if (vectorImage == null)
            vectorImage = new VectorImage(oldWidth, oldHeight);

        int numberOfPoints = oldWidth * oldHeight;

        int[] xPoints = new int[numberOfPoints];
        int[] yPoints = new int[numberOfPoints];
        int[] brightness = new int[numberOfPoints];

        int index = 0;

        for (int y = 0; y < oldHeight; y++) {
            for (int x = 0; x < oldWidth; x++) {
                int yPos = y;
                int xPos = ( (y%2) == 0) ? x  : oldWidth  - x - 1;

                xPoints[index] = xPos;
                yPoints[index] = yPos;

                int r = ( image.getRGB( (y%2)==0 ? x : oldWidth-x-1, y) >> 16) & 0xFF;
                brightness[index] = (int)( ((float)r / 256f) * numberOfGraylevels );

                index++;
            }
        }

        for (int i = 1; i < numberOfPoints; i++) {
            vectorImage.addLine( xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i], brightness[i]);
        }

        return new State( image, imageData.getImageArray(), vectorImage );
    }
}
