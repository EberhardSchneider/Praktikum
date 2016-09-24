package Algorithm;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;

/**
 * Created by eberh_000 on 24.09.2016.
 */
public class MakeRaster implements iAlgorithm {

    int rasterX = 1 00;
    int rasterY = 100;

    public BufferedImage processImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(rasterX, rasterY, TYPE_BYTE_GRAY);

        int stepX = (int) width / rasterX;
        int stepY = (int) height / rasterY;

        // loop for new pixels
        for (int rx = 0; rx < rasterX; rx++ )
            for (int ry = 0; ry < rasterY; ry++ ) {

                int startX = rx*stepX;
                int startY = ry*stepY;

                int sum = 0;

                for (int x = startX; x < (startX + stepX); x++)
                    for (int y = startY; y < (startY + stepY); y++) {

                        x = x>(width-1) ? (width-1) : x;
                        y = y>(height-1) ? (height-1) : y;

                        sum += image.getRGB( x,y);
                    }

                result.setRGB( rx,ry, (int)( sum / (stepX*stepY)));

            }


            return result;
    }

    public void setParam(int x, int y) {
        rasterX = x;
        rasterY = y;
    }
}
