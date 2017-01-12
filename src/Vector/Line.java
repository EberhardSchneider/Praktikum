package Vector;

import java.awt.*;

/**
 * Created by eberh_000 on 27.11.2016.
 */
class Line implements iVectorElement {

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

    public FloatPoint[] getCoordinates() {
        FloatPoint[] result = new FloatPoint[2];
        result[0] = new FloatPoint(x1, y1);
        result[1] = new FloatPoint(x2, y2);
        return result;
    }
}
