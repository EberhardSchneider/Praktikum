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

    int width = 800;
    int height = 600;

    public VectorImage() {

    }

    public VectorImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addLine(int x1, int y1, int x2, int y2) {
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
    public BufferedImage getImage(float scale) {
        BufferedImage image = new BufferedImage((int)(width * scale), (int)(height * scale), BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = image.createGraphics();

        for (int x = 0; x < width * scale; x++)
            for (int y = 0; y < height * scale; y++)
                image.setRGB(x, y, 0xFFFFFF);


        drawVectorImage(g,scale);

        return image;
    }

    public BufferedImage drawInImage(BufferedImage image, float scale) {
        Graphics2D g = image.createGraphics();

        drawVectorImage(g, scale);

        return image;
    }


    public BufferedImage getImage(int width, int height) {
        this.width = width;
        this.height = height;
        return getImage(1f);
    }

    void drawVectorImage(Graphics2D g, float scale) {
        for (iVectorElement e : elements) {
            FloatPoint[] p = e.getCoordinates();


            g.setColor( Color.BLACK );
            g.setStroke( new BasicStroke( 1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            for (int i = 1; i < p.length; i++) {
                g.setStroke( new BasicStroke( 1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));

                g.drawLine( (int)(p[i-1].x * scale), (int)(p[i-1].y * scale), (int)(p[i].x * scale), (int)(p[i].y * scale) );

            }
        }
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
