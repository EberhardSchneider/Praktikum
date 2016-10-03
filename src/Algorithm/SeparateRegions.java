package Algorithm;

/**
 * Created by eberh_000 on 03.10.2016.
 */
public class SeparateRegions implements iArrayAlgorithm {

    public int[][] processArray(int[][] array) {

        if ( ( array == null) || ( array[0] == null )) return null;

        int height = array.length;
        int width = array[0].length;

        int[][] I = new int[height][width];
        int[][] D = new int[height][width];

        // First pass of algorithm
        for (int i = 1; i < height; i++)
            for (int j = 1; j < width; j++) {
                if ( array[i][j] == 0) {
                    I[i][j] = 0;
                }
                else if ( (i > 1) || (j > 1) || (array[i][j] == 1) ) {
                    I[i][j] = Math.min( I[i-1][j]+1, I[i][j-1]+1 );
                }
                else
                    I[i][j] =width + height;
            }

        // Second pass of algorithm
    for (int i = (height - 2); i >= 0; i--)
        for (int j = (width - 2); j >= 0; j--) {
            int min = Math.min( I[i][j], I[i+1][j] + 1);
            D[i][j] = Math.min( I[i][j+1] + 1, min);
        }



        return D;
    }
}
