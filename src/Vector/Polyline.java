package Vector;

import java.awt.*;

/**
 * Created by eberh_000 on 27.11.2016.
 */
class Polyline implements VectorImage.iVectorElement {

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
