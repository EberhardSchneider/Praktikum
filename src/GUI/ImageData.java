package GUI;

import Vector.VectorImage;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * ImageData stores an image in three different formats and can convert between them.
 */
public class ImageData {
    BufferedImage image;
    int[][] imageArray;
    VectorImage vectorImage;

    /**
     *  If initialized with BufferedImage imageArray should be calculated instantly
     * @param i
     */
    public ImageData(BufferedImage i) {
        image = i;
        convertImageToImageArray(128);
    }

    public ImageData(BufferedImage i, int[][] iArray, VectorImage vImage) {
        this.image = i;
        this.imageArray = iArray;
        this.vectorImage = vImage;
    }

    public BufferedImage getImage() { return image; }
    public int[][] getImageArray() { return imageArray; };
    public VectorImage getVectorImage() { return vectorImage; }

    void convertImageToImageArray(int threshold) {

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[width][height];


        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {

                int argb = image.getRGB( j, i);
                int alpha = (argb >> 24 ) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = (argb ) & 0xFF;
                int grayValue = (int)(red * 0.299 + green * 0.587 + blue * 0.114);


                if ( grayValue < threshold) {
                    result[j][i] = 1;
                } else
                    result[j][i] = 0;
            }

        imageArray = result;
    }

}
