package Vector;

/**
 * Created by eberh_000 on 30.11.2016.
 */
public class FloatPoint {
    float x;
    float y;

    public FloatPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override public String toString() {
            return "x: " + x + "  y: " + y;
        }
}
