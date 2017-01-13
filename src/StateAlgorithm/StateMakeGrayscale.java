package StateAlgorithm;
import GUI.ImageData;
import GUI.State;

import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 28.11.2016.
 */
public class StateMakeGrayscale implements iStateAlgorithm {
    public State processImage(ImageData imageData) {
        BufferedImage image = imageData.getImage();
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[][] imageArray = new int[width][height];



        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = image.getRGB( x, y);
                int a = (argb >> 24 ) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = (argb ) & 0xFF;
                int rgb = (int)(r * 0.299 + g * 0.587 + b * 0.114);

                int res = (a << 24) | ( rgb << 16) | (rgb << 8) | rgb;

                newImage.setRGB(x, y, res);
                imageArray[x][y] = res;
            }
        }

        return new State( newImage, imageArray, imageData.getVectorImage() );
    }
}
