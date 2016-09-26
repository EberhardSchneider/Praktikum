package Algorithm;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by eberh_000 on 25.09.2016.
 */
public class FillHilbert implements iAlgorithm {

    private int scale = 15;

    final static class Point {
        int x;
        int y;
        public Point( int x, int y ) {
            x = x;
            y = y;
        }
    }

    BufferedImage processImage(BufferedImage image) {

        BufferedImage result = new BufferedImage( image.getWidth(), image.getHeight(), image.getType() );

        ArrayList<Point> points = new ArrayList<>();



    }
}
