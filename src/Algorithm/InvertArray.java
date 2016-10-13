package Algorithm;

/**
 * Created by eberh_000 on 12.10.2016.
 */
public class InvertArray implements iArrayAlgorithm {
    public int[][] processArray(int[][] array) {
        int width = array.length;
        int height = array[0].length;

        int[][] c = new int[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if ( array[x][y] > 0) {
                    c[x][y] = 0;
                }
                else {
                    c[x][y] = 1;
                }
            }

            return c;
    }
}
