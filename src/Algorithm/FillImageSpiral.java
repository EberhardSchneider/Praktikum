package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 24.09.2016.
 */
public class FillImageSpiral implements iAlgorithm {

    private int scale = 15;

    static final class Point {
        int x;
        int y;
        int brightness;
    }

    private Point getPoint(BufferedImage image, int x, int y) {
        Point p = new Point();

        p.x = x * scale;
        p.y = y * scale;

        int r = ( image.getRGB(  x, y) >> 16) & 0xFF;
        p.brightness = (int)( (float)r / 20f);

        return p;
    }

    public BufferedImage processImage(BufferedImage image) {

        int oldWidth = image.getWidth();
        int oldHeight = image.getHeight();
        BufferedImage result = new BufferedImage( oldWidth * scale, oldHeight * scale, image.getType() );

        Graphics2D g = result.createGraphics();

        int numberOfPoints = oldWidth * oldHeight;

        Point[] points = new Point[numberOfPoints];

        int index = 0;
        int xPos = 0;
        int yPos = 0;

        int dX = 1;
        int dY = 0;

        int xMin=0;
        int xMax = oldWidth-1;
        int yMin = 0;
        int yMax = oldHeight-1;


        boolean finished = false;

        while (!finished) {

            while (xPos < xMax) {
                xPos++;
                points[index] = getPoint( image, xPos, yPos);
                index++;
            }
            yMin++;

            while (yPos < yMax) {
                yPos++;
                points[index] = getPoint( image, xPos, yPos);
                index++;
            }
            xMax--;

            while (xPos > xMin) {
                xPos--;
                points[index] = getPoint( image, xPos, yPos);
                index++;
            }
            yMax--;

            while (yPos > yMin) {
                yPos--;
                points[index] = getPoint( image, xPos, yPos);
                index++;
            }
            xMin++;

            if ( (xMin >= xMax) || (yMin >= yMax) ) {
                finished = true;
            }
        }



        BasicStroke[] stroke = new BasicStroke[15];
        for (int i = 0; i<15; i++)
            stroke[i] = new BasicStroke( 16-i, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);

        g.setColor( Color.BLACK );
        for (int i = 1; i < numberOfPoints-1; i++) {
            g.setStroke( stroke[ points[i].brightness ] );
            g.drawLine( points[i-1].x, points[i-1].y, points[i].x, points[i].y);
        }

        return result;
    }
}
