package Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;

/**
 * Class to store the Vector informations.
 * Holds a list of SVGElements, which can be
 * Polygon or Line.
 */
public class VectorImage {

    ArrayList<iVectorElement> elements = new ArrayList<>();

    interface iVectorElement {
        public String toSVG();
        public Point[] getCoordinates();
    }











    public void addLine( int x1, int y1, int x2, int y2) {
        elements.add( new Line(x1, y1, x2, y2) );
    }
    public void addLine( int x1, int y1, int x2, int y2, int stroke) {
        elements.add( new Line(x1, y1, x2, y2, stroke) );
    }

    public void addPolygon( int[] x, int[] y, int nPoints) {
        if ( (x.length == nPoints) && (y.length == nPoints) ) {
            elements.add( new Polygon( x, y, nPoints ));
        }
    }

    public File getFile() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("<?xml version=\"1.0\"?>");
        sb.append(System.getProperty("line.separator"));
        sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD Vector 1.1//EN\"");
        sb.append(System.getProperty("line.separator"));
        sb.append("\"http://www.w3.org/Graphics/Vector/1.1/DTD/svg11.dtd>\"");
        sb.append(System.getProperty("line.separator"));
        sb.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" width=\"534\" height=\"698\">");
        sb.append(System.getProperty("line.separator"));

        // Now add all elements
        for (iVectorElement e : elements) {
            sb.append( e.toSVG());
            sb.append(System.getProperty("line.separator"));
        }

        sb.append("</svg>");


        File f = new File( sb.toString() );
        return f;
    }

    public String getSVGString() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("<?xml version=\"1.0\"?>");
        sb.append(System.getProperty("line.separator"));
        sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD Vector 1.1//EN\"");
        sb.append(System.getProperty("line.separator"));
        sb.append("\"http://www.w3.org/Graphics/Vector/1.1/DTD/svg11.dtd\">");
        sb.append(System.getProperty("line.separator"));
        sb.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" width=\"534\" height=\"698\">");
        sb.append(System.getProperty("line.separator"));

        // Now add all elements
        for (iVectorElement e : elements) {
            sb.append( "\t" + e.toSVG());
            sb.append(System.getProperty("line.separator"));
        }

        sb.append("</svg>");


        return sb.toString();
    }

    /**
     * method to merge two svg objects
     * @param vectorImage2 svg Object to be merged with this
     */
    public void addSVG( VectorImage vectorImage2) {
        this.elements.addAll( vectorImage2.elements );
    }



    /**
     * returns a buffered image which shows the svg elements stored
     * @return image with svg elements
     */
    public BufferedImage getImage(int width, int height) {
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = image.createGraphics();

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB( x, y, 0xFFFFFF);
        // calculate max x / y - coordinates in svg elements
        int maxX = 0;
        int maxY = 0;

        for (iVectorElement e : elements) {
            Point[] p = e.getCoordinates();


            g.setColor( Color.BLACK );
            g.setStroke( new BasicStroke( 2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            for (int i = 1; i < p.length; i++) {
                g.drawLine( p[i-1].x, p[i-1].y, p[i].x, p[i].y);

            }


        }

        return image;
    }

    public void deleteLinesShorterThan(double maxLength) {
        ArrayList<iVectorElement> toBeDeleted = new ArrayList<>();

        for (iVectorElement e : this.elements) {
            if (e instanceof Line) {
                Line line = (Line)e;
                double length  = Math.sqrt( Math.pow( (line.x2-line.x1), 2) + Math.pow( (line.y2-line.y1), 2));
                if (length < maxLength)
                    toBeDeleted.add( e );
            }
        }

        for (iVectorElement e : toBeDeleted) {
            this.elements.remove( e );
        }
    }

}
