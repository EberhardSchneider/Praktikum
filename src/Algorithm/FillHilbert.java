package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;

/**
 * Created by eberh_000 on 25.09.2016.
 */
public class FillHilbert implements iAlgorithm {

    // Parameters
    private int maxIterations = 10;
    private int minIterations = 3;

    private int lowerThreshold = 50;
    private int sensibility = 10;

    private int scale = 15;
    public ArrayList<Point> points = new ArrayList<>();

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

    public int getBrightnessOfQuadrant(double x, double y, double width, double height) {
        int sum = 0;

        if (width < 0) {
            x += width;
            width = -width;
        }

        if (height < 0) {
            y += height;
            height = -height;
        }

        int nPixels = (int)( width * height );

        for (int i = (int)x; i < ( x + width); i++) {
            for (int j = (int)y; j < (y + height); j++) {
                Color c = new Color( image.getRGB( i/scale, j/scale) );
                sum += c.getGreen();
            }
        }

        return sum/nPixels;
    }

    void hilbertIteration( double x, double y, double xi, double yi, double xj, double yj, int n) {

       if ( (n == 0)   || (n < maxIterations - minIterations) && (getBrightnessOfQuadrant( x, y, xi+yi, xj+yj ) > 109 + n*200/maxIterations))
           points.add(new Point((int) (x + (xi + yi) / 2), (int) (y + (xj + yj) / 2)));
       else {

            hilbertIteration(x,      y,      yi/2,       yj/2,       xi/2,       xj/2,   n-1);
            hilbertIteration(x+xi/2,  y+xj/2,  xi/2,       xj/2,       yi/2,       yj/2,   n-1);
            hilbertIteration(x+xi/2+yi/2,      y+xj/2+yj/2, xi/2, xj/2, yi/2, yj/2, n-1);
            hilbertIteration( x+xi/2+yi, y+xj/2+yj, -yi/2,-yj/2, -xi/2, -xj/2, n-1);

       }

    }

    public BufferedImage processImage(BufferedImage image) {

        this.image = image;
        this.colorModel = image.getColorModel();

        width = image.getWidth();
        height = image.getHeight();

        BufferedImage result = new BufferedImage( scale * width, scale * height, image.getType() );

        Graphics2D g = result.createGraphics();


        hilbertIteration( 0, 0, scale * width , 0, 0, scale * height, maxIterations);


        g.setStroke(  new BasicStroke( 4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND) );
        g.setColor( Color.BLACK );

        Point lastPoint = null;
        for (Point p : points) {
            if (lastPoint != null)
                g.drawLine( lastPoint.x, lastPoint.y, p.x, p.y);
            lastPoint = p;
        }

        return result;
    }
}
