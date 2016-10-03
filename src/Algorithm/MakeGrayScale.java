package Algorithm;

import java.awt.image.BufferedImage;

/**
 * This algorithm convverts an im age in grayscale.
 */
public class MakeGrayScale implements iImageAlgorithm {

    public BufferedImage processImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = image.getRGB( x, y);
                int a = (argb >> 24 ) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = (argb ) & 0xFF;
                int rgb = (int)(r * 0.299 + g * 0.587 + b * 0.114);

                int res = (a << 24) | ( rgb << 16) | (rgb << 8) | rgb;

                result.setRGB(x, y, res);
            }
        }

        return result;
    }
}
