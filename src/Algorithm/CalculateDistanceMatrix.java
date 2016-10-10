package Algorithm;

import sun.rmi.runtime.Log;

import java.util.logging.Logger;

/**
 * Calculates distance matrix from black&white image array.
 */
public class CalculateDistanceMatrix implements iArrayAlgorithm {

    public int[][] processArray(int[][] array) {

        Logger log = Logger.getLogger( CalculateDistanceMatrix.class.getName() );

        if ( ( array == null) || ( array[0] == null )) return null;

        int width = array.length;
        int height = array[0].length;


        int[][] I = new int[width][height];
        int[][] D = new int[width][height];

        // First pass of algorithm
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {

                if ( array[i][j] == 0) {
                    I[i][j] = 0;
                }
                else
                if ( ( (i > 0) || (j > 0) ) && (array[i][j] == 1) ) {
                    if ( (i != 0) && ( j != 0) )
                        I[i][j] = Math.min( I[i-1][j]+1, I[i][j-1]+1 );
                    else if ( (i == 0) && ( j!= 0) )
                            I[i][j] = I[i][j-1] + 1;
                    else if ( (i != 0) && (j == 0))
                            I[i][j] = I[i-1][j];
                }
                else
                    I[i][j] = width + height;
            }

        // Second pass of algorithm
    for (int j = (height - 1); j >= 0; j--)
        for (int i = (width - 1); i >= 0; i--) {

            if ( ( j != height-1 ) && (i != width-1 ) ) {
                D[i][j] = min3( I[i][j], D[i+1][j] + 1, D[i][j + 1] + 1);
            }
            else if ( ( j != height-1) && (i == width-1 ) )
                D[i][j] = Math.min( D[i][j+1] + 1, I[i][j] );
            else if ( (j == height-1) && (i != width-1 ) )
                D[i][j] = Math.min( I[i][j], D[i+1][j] + 1);
            else
                D[i][j] = I[i][j];

        }

        return D;
    }

    private int  min3(int value1, int value2, int value3) {
        int minTmp = Math.min( value1, value2 );
        return Math.min( value3, minTmp);
    }
}
