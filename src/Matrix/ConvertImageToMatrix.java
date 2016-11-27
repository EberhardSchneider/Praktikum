package Matrix;

import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 07.10.2016.
 */
public class ConvertImageToMatrix implements iMatrixAlgorithm {

    BufferedImage image;
    int threshold;

    /**
     * Constructor of a ConvertImageToMatrix Algorithm
     * @param image BufferedImage that  is converted.
     * @param threshold If a pixel is brighter than threshold the resulting array
     *                  contains a 1.
     */
    public ConvertImageToMatrix(BufferedImage image, int threshold) {
            this.image = image;
            this.threshold = threshold;
    }

    public DistanceMatrix processMatrix(DistanceMatrix matrix) {

        int width = image.getWidth();
        int height = image.getHeight();
        DistanceMatrix result = new DistanceMatrix( width, height);


        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int greenValue =  ( image.getRGB( j, i ) >> 8 ) & 0xFF;
                int grayValue = (int)(greenValue * (1 / 0.587));


                if ( grayValue > threshold) {
                    result.setValue( j, i, 1);
                } else
                    result.setValue( j, i, 0);
            }

        return result;
    }

}
