package SVG;

import java.util.ArrayList;
import java.io.*;

/**
 * Class to store the SVG informations.
 * Holds a list of SVGElements, which can be
 * Polygon or Line.
 */
public class SVG {

    ArrayList<SVGElement> elements = new ArrayList<SVGElement>();

    interface SVGElement {
        public String toSVG();
    }

    static final class Line implements SVGElement {

        int x1,x2,y1,y2;

        public Line( int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public String toSVG() {
            StringBuilder sb = new StringBuilder();
            sb.append("<line style=\"fill:none;stroke:rgb(0,0,0);stroke-linejoin=bevel\" x1=\"");
            sb.append( x1 );
            sb.append("\" y1 = \"");
            sb.append( y1 );
            sb.append("\" x1 = \"");
            sb.append( x1 );
            sb.append("\" y2 = \"");
            sb.append( y2 );
            sb.append("\" />");

            return sb.toString();
        }
    }

    static final class Polygon  implements SVGElement {

        int[] x;
        int[] y;
        int nPoints;

        public Polygon ( int[] x, int[] y, int nPoints ) {
            this.x = x;
            this.y = y;
            this.nPoints = nPoints;
        }

        public String toSVG() {
            StringBuilder sb = new StringBuilder();
            sb.append("<path style=\"fill:none;stroke:rgb(0,0,0);stroke-linejoin=bevel ");

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
    }

    public void addLine( int x1, int x2, int y1, int y2) {
        elements.add( new Line(x1, y1, x2, y2) );
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
        sb.append((System.getProperty("line.separator"));
        sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"");
        sb.append((System.getProperty("line.separator"));
        sb.append("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"");
        sb.append((System.getProperty("line.separator"));
        sb.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" width=\"534\" height=\"698\">");
        sb.append((System.getProperty("line.separator"));

        // Now add all elements
        for (SVGElement e : elements) {
            sb.append( e.toSVG());
            sb.append((System.getProperty("line.separator"));
        }

        sb.append("</svg>");

        File f = new File( sb.toString() );
        return f;
    }

}
