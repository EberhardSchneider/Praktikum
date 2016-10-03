package Algorithm;

import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 03.10.2016.
 */
public class ConvertArrayToImage implements iImageAlgorithm {

    private int[][] array;


    public ConvertArrayToImage( int[][] array ) {
        this.array = array;
    }


    public BufferedImage processImage(BufferedImage image) {

        int height = array.length;
        int width = array[0].length;

        BufferedImage result = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {



                if ( array[i][j] == 1) {
                    result.setRGB( i, j, 0xFFFFFF );
                } else
                    result.setRGB( i, j, 0 );

            }

        return result;

    }
}
