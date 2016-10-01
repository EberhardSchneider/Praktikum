package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;

/**
 * Created by eberh_000 on 25.09.2016.
 */
public class FillHilbert implements iAlgorithm {

    private int scale = 15;
    private ArrayList<Point> points = new ArrayList<>();

    BufferedImage image;
    private int width;
    private int height;

    ColorModel colorModel;

    final static class Point {
        int x;
        int y;
        public Point( int x, int y ) {
            this.x = x;
            this.y = y;
        }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append( "x: " + String.valueOf(x) + "  y:  " + String.valueOf(y));
            return sb.toString();
        }
    }

    public int getBrightnessOfQuadrant( double x, double y, double width, double height) {
        int sum = 0;
        //if ( x + width > image.getWidth() ) width = image.getWidth() - x;
        //if ( y + height > image.getHeight() ) height = image.getHeight() - y;


        int nPixels = (int)( ( Math.abs(width*height) < 0.5 ) ? 1 : width * height );
        for (int i = (int)x; i < ( x + width); i++) {
            for (int j = (int)y; j < (y + height); j++) {
                Color c = new Color( image.getRGB( i, j) );
                sum += c.getGreen();

            }
        }
    System.out.println(nPixels);
        return sum/nPixels;
    }

    void hilbertIteration( double x, double y, double xi, double yi, double xj, double yj, int n) {
       if (n == 0)  {
           points.add( new Point((int)(x + (xi+yi)/2),(int)(y + (xj+yj)/2) ));
       }
       else {
           if ( (getBrightnessOfQuadrant( x, y, yi/2, xj/2 ) < ( 123 + n * 122/7 )) )
                hilbertIteration(x,      y,      yi/2,       yj/2,       xi/2,       xj/2,   n-1);
           else
               hilbertIteration(x,      y,      yi/2,       yj/2,       xi/2,       xj/2,   0);

           if ( (getBrightnessOfQuadrant( x+xi/2, y+xj/2, xi/2, yj/2 ) < ( 123 + n * 122/7 )) )
            hilbertIteration(x+xi/2,  y+xj/2,  xi/2,       xj/2,       yi/2,       yj/2,   n-1);
           else
               hilbertIteration(x+xi/2,  y+xj/2,  xi/2,       xj/2,       yi/2,       yj/2,   0);


           if ( (getBrightnessOfQuadrant( x+xi/2+yi/2, y+xj/2+yj/2, xi/2, yj/2 ) < ( 123 + n * 122/7 )))
               hilbertIteration(x+xi/2+yi/2,      y+xj/2+yj/2, xi/2, xj/2, yi/2, yj/2, n-1);
           else
               hilbertIteration(x+xi/2+yi/2,      y+xj/2+yj/2, xi/2, xj/2, yi/2, yj/2, 0);

           if ( (getBrightnessOfQuadrant( x+xi/2+yi, y+xj/2+yj, -yi/2, -xj/2) < ( 123 + n * 122/7 )))
               hilbertIteration( x+xi/2+yi, y+xj/2+yj, -yi/2,-yj/2, -xi/2, -xj/2, n-1);
           else
               hilbertIteration( x+xi/2+yi, y+xj/2+yj, -yi/2,-yj/2, -xi/2, -xj/2, 0);

       }

    }

    public BufferedImage processImage(BufferedImage image) {

        this.image = image;
        this.colorModel = image.getColorModel();

        width = image.getWidth();
        height = image.getHeight();

        BufferedImage result = new BufferedImage( width, height, image.getType() );

        Graphics2D g = result.createGraphics();



        hilbertIteration( 0, 0, width , 0, 0,    height,7);

        Point lastPoint = null;
        for (Point p : points) {
            if (lastPoint != null)
                g.drawLine( lastPoint.x, lastPoint.y, p.x, p.y);
            lastPoint = p;
        }

        return result;
    }
}
