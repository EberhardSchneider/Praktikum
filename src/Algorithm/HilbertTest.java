package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 02.10.2016.
 */
public class HilbertTest implements iAlgorithm {

    private int scale = 1;
    Graphics2D g;

    public BufferedImage processImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage( scale * width, scale * height, image.getType() );

        g = result.createGraphics();
        g.setStroke(  new BasicStroke( 4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND) );
        g.setColor( Color.WHITE);

        hilbertIteration(20,20,(scale*width-100),0,0,(scale*height-100),1);

        return result;

    }

    void hilbertIteration( double x, double y, double xi, double yi, double xj, double yj, int n) {

        if  (n == 0) {
            g.setColor(Color.WHITE);
            if ( (xi+yi < 0) || (xj+yj <  0 )) {
                System.out.println("Jau!");
                System.out.println( "Param: " + x + ", "+ y + ", " + (xi+yi) + ", " + (xj+yj));
                g.setColor(Color.RED);
            }

            g.drawRect((int) x, (int) y, (int) (xi + yi), (int) (xj + yj));
            g.drawOval((int)x, (int)y, 5, 5);
        }
        else {

            hilbertIteration(x,      y,      yi/2,       yj/2,       xi/2,       xj/2,   n-1);
            hilbertIteration(x+xi/2,  y+xj/2,  xi/2,       xj/2,       yi/2,       yj/2,   n-1);
            hilbertIteration(x+xi/2+yi/2,      y+xj/2+yj/2, xi/2, xj/2, yi/2, yj/2, n-1);
            hilbertIteration( x+xi/2+yi, y+xj/2+yj, -yi/2,-yj/2, -xi/2, -xj/2, n-1);

        }

    }
}
