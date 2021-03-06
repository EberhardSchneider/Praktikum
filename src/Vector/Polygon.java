package Vector;

import java.awt.*;

/**
 * Created by eberh_000 on 27.11.2016.
 */
class Polygon  implements iVectorElement {

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
        sb.append("<path style=\"fill:none;stroke:rgb(0,0,0);stroke-linejoin=bevel\" d=\"");

        for (int i = 0; i < nPoints; i++) {
            if (i == 0) {
                sb.append("M");
            } else {
                sb.append("L");
            }
            sb.append(x[i] + " " + y[i] + " ");
        }
        sb.append("\" />");

        return sb.toString();
    }

    public FloatPoint[] getCoordinates() {
        FloatPoint[] result = new FloatPoint[ nPoints ];
        for (int i = 0; i < nPoints; i++) {
            result[i] = new FloatPoint( x[i], y[i] );
        }
        return result;
    }
}
