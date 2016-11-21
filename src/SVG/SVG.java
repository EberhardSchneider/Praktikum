package SVG;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;

/**
 * Class to store the SVG informations.
 * Holds a list of SVGElements, which can be
 * Polygon or Line.
 */
public class SVG {

    ArrayList<SVGElement> elements = new ArrayList<>();

    interface SVGElement {
        public String toSVG();
        public Point[] getCoordinates();
    }

    static final class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override public String toString() {
            return "x: " + x + "  y: " + y;
        }
    }

    static final class Line implements SVGElement {

        int x1, x2, y1, y2;
        int stroke = 1;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public Line(int x1, int y1, int x2, int y2, int stroke) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.stroke = stroke;
        }



        public String toSVG() {
            StringBuilder sb = new StringBuilder();
            sb.append("<line style=\"fill:none; stroke:rgb(0,0,0); stroke-linejoin=bevel\" x1=\"");
            sb.append(x1);
            sb.append("\" y1=\"");
            sb.append(y1);
            sb.append("\" x2=\"");
            sb.append(x2);
            sb.append("\" y2=\"");
            sb.append(y2);
            sb.append("\" stroke-width=\"");
            sb.append( stroke );
            sb.append("\" />");

            return sb.toString();
        }

        public Point[] getCoordinates() {
            Point[] result = new Point[2];
            result[0] = new Point(x1, y1);
            result[1] = new Point(x2, y2);
            return result;
        }
    }

    static final class Polygon  implements SVGElement {

        int[] x;
        int[] y;
        int nPoints;

        public Polygon(int[] x, int[] y, int nPoints) {
            this.x = x;
            this.y = y;
            this.nPoints = nPoints;
        }

        public String toSVG() {
            StringBuilder sb = new StringBuilder();
            sb.append("<path style=\"fill:none;stroke:rgb(0,0,0);stroke-linejoin=bevel\" ");

            for (int i = 0; i < nPoints; i++) {
                if (i == 0) {
                    sb.append("M");
                } else {
                    sb.append("L");
                }
                sb.append(x[i] + " " + y[i] + " ");
            }
            sb.append(" />");

            return sb.toString();
        }

        public Point[] getCoordinates() {
            Point[] result = new Point[ nPoints ];
            for (int i = 0; i < nPoints; i++) {
                result[i] = new Point( x[i], y[i] );
            }
            return result;
        }
    }

        static final class Polyline implements SVGElement {

            int[] x;
            int[] y;
            int nPoints;

            public Polyline ( int[] x, int[] y, int nPoints ) {
                this.x = x;
                this.y = y;
                this.nPoints = nPoints;
            }

            public String toSVG() {
                StringBuilder sb = new StringBuilder();
                sb.append("<polyline style=\"fill:none;stroke:rgb(0,0,0);stroke-linejoin=bevel\" ");

                for (int i = 0; i < nPoints; i++) {
                    if (i == 0) {
                        sb.append("M");
                    }
                    else {
                        sb.append("L");
                    }
                    sb.append( x[i] + " " + y[i] + " ");
                }
                sb.append(" />");

                return sb.toString();
            }



        public Point[] getCoordinates() {
            Point[] result = new Point[ nPoints ];
            for (int i = 0; i < nPoints; i++) {
                result[i] = new Point( x[i], y[i] );
            }
            return result;
        }
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
        sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"");
        sb.append(System.getProperty("line.separator"));
        sb.append("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"");
        sb.append(System.getProperty("line.separator"));
        sb.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" width=\"534\" height=\"698\">");
        sb.append(System.getProperty("line.separator"));

        // Now add all elements
        for (SVGElement e : elements) {
            sb.append( e.toSVG());
            sb.append(System.getProperty("line.separator"));
        }

        sb.append("</svg>");


        File f = new File( sb.toString() );
        return f;
    }

    /**
     * method to merge two svg objects
     * @param svg2 svg Object to be merged with this
     */
    public void addSVG( SVG svg2 ) {
        this.elements.addAll( svg2.elements );
    }



    /**
     * returns a buffered image which shows the svg elements stored
     * @return image with svg elements
     */
    public BufferedImage getImage(int width, int height) {
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = image.createGraphics();
        // calculate max x / y - coordinates in svg elements
        int maxX = 0;
        int maxY = 0;

        for (SVGElement e : elements) {
            Point[] p = e.getCoordinates();




            Random randomNumber = new Random();
            g.setColor(new Color(randomNumber.nextFloat(),
                    randomNumber.nextFloat(), randomNumber.nextFloat() ));
            g.setColor( Color.GREEN );
            g.setStroke( new BasicStroke( 1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            for (int i = 1; i < p.length; i++) {
                g.drawLine( p[i-1].x, p[i-1].y, p[i].x, p[i].y);

            }
            // add last Connection if element is Polygon
            if (p.length > 2) {
                g.drawLine( p[0].x, p[0].y, p[p.length-1].x, p[p.length-1].y);
            }

        }

        return image;
    }

    public void deleteLinesShorterThan(double maxLength) {
        ArrayList<SVGElement> toBeDeleted = new ArrayList<>();

        for (SVGElement e : this.elements) {
            if (e instanceof Line) {
                Line line = (Line)e;
                double length  = Math.sqrt( Math.pow( (line.x2-line.x1), 2) + Math.pow( (line.y2-line.y1), 2));
                if (length < maxLength)
                    toBeDeleted.add( e );
            }
        }

        for (SVGElement e : toBeDeleted) {
            this.elements.remove( e );
        }
    }

}
