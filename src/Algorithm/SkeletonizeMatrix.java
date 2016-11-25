package Algorithm;

/**
 * Created by eberh_000 on 24.11.2016.
 */
public class SkeletonizeMatrix implements iArrayAlgorithm {
    int[][] array;
    int[][] marked;
    int width;
    int height;

    @Override
    public int[][] processArray(int[][] a) {
        this.array = a;

        width = array.length;
        height = array[0].length;

        marked = new int[width][height];

        // algorithm runs again, if something was changed
        boolean isChanged = true;
        int index = 1;
        while (isChanged) {
            isChanged = false;

            for (int x = 0; x < width; x++)
                for (int y  = 0; y < height; y++) {
                    markPixels( x, y);
                }

            for (int x = 0; x < width; x++)
                for (int y  = 0; y < height; y++) {
                    if (marked[x][y] != 0) {
                        array[x][y] = 0;
                        marked[x][y] = 0;
                        isChanged = true;
                    }
                }
        }

        return array;
    }

    /**
     * checks Pixel with index xIndex, yIndex and marks it in marked[][] if necessary
     *
     * @param xIndex
     * @param yIndex
     *
     */
   void markPixels(int xIndex, int yIndex) {

        // first calculate x_1 to x_8
        int[] xPixel = new int[9];
        xPixel[1] = (xIndex < width-1) ? array[xIndex+1][yIndex] : 0;
        xPixel[2] = (xIndex < width-1) && (yIndex > 0) ? array[xIndex+1][yIndex-1] : 0;
        xPixel[3] = (yIndex > 0) ? array[xIndex][yIndex-1] : 0;
        xPixel[4] = (xIndex > 0) && (yIndex > 0) ? array[xIndex-1][yIndex-1] : 0;
        xPixel[5] = (xIndex > 0) ? array[xIndex-1][yIndex] : 0;
        xPixel[6] = (xIndex > 0) && (yIndex < height-1) ? array[xIndex-1][yIndex+1] : 0;
        xPixel[7] = (yIndex < height-1) ? array[xIndex][yIndex+1] : 0;
        xPixel[8] = (xIndex < width-1) && (yIndex < height-1) ? array[xIndex+1][yIndex+1] : 0;


       // cond 1: p is black
       boolean condition1 = (array[xIndex][yIndex] == 1);

       // condition 2: p in contour => at least one direct neightbour is white
       // count number of direct neighbours
       int nn4 = xPixel[1] + xPixel[3] + xPixel[5] + xPixel[7];
       boolean condition2 = (nn4 > 0);


       // condition3:   at least two black pixels in neighbourhood
       // count number of  black neighbours nn
       int nn8 = 0;
       for (int i = 1; i <= 8; i++) {
           if ( xPixel[i] == 1 ) {
               nn8++;
           }
       }
       boolean condition3 = (nn8 <= 7);


       // condition 4: at least one black pixel in neighbourhood ist not marked
       // check if at least one black pixel in neighbourhood is not yet marked (condition 4)
       boolean blackPixelNotMarked = false;
       int xStart = (xIndex > 0) ? (xIndex - 1) : 0;
       int yStart = (yIndex > 0) ? (yIndex - 1) : 0;
       int xEnd = (xIndex < width - 1) ? xIndex + 1 : xIndex;
       int yEnd = (yIndex < height - 1) ? yIndex + 1 : yIndex;

       for (int x = xStart; x <= xEnd; x++)
           for (int y = yStart; y <= yEnd; y++) {
               if ( (x == xIndex) && (y == yIndex) )
                   continue;
               if ( (array[x][y] == 1) &&  (marked[x][y] == 0))  // is black pixel not marked?
                   blackPixelNotMarked = true;
           }
        boolean condition4 = blackPixelNotMarked;


       // condition 5: crossing number xH = 1
       // calculate crossing number xH
        int xH = calculateXH(xPixel);
        boolean condition5 = ( xH == 1);




       // check condition 6
       boolean condition6 = true;
       if ((yIndex > 0) && (marked[xIndex][yIndex-1] == 1)) {     // if x_3 is marked
           // condition6 = ( xPixel[4] == 1 || xPixel[5] == 1 ) != ( xPixel[2] == 0 || xPixel[1] == 0); // equivalent to (6)
           xPixel[3] = 0;       // check if xH changes, when x_3 is switched to white
           if (xH == calculateXH(xPixel)) {
               condition6 = true;
           } else {
               condition6 = false;
           }
           xPixel[3] = 1;
       }

       // check condition 7
       boolean condition7 = true;
       if ( (xIndex > 0) && (marked[xIndex-1][yIndex] == 1)) {     // if x_5 is marked
           //condition7 = ( xPixel[6] == 1 || xPixel[7] == 1 ) != ( xPixel[4] == 0 || xPixel[3] == 0); // equivalent to (7)
           xPixel[5] = 0;       // check if xH changes, when x_5 is switched to white
           if (xH == calculateXH(xPixel)) {
               condition7 = true;
           } else {
               condition7 = false;
           }
           xPixel[5] = 1;
       }



       if ( condition1 //  (1) p black
                && condition2            // (2) p in contour
                && condition3            // (3) at least two black pixels in neighbourhood
                && condition4 // (4) at least one black pixel in neighbourhood not marked
                && condition5            // (5) xH(p) = 1
                && condition6 // (6) equivalent to condition 6
                && condition7 // (7) equivalent to condition 7
                ) {
            marked[xIndex][yIndex] = 1;
        }




    }

    /**
     * calculates the crossing number of a pixel from a given array of pixels x_1 to x_8
     * @param p pixels in neighbourhood of p
     * @return crossing number X_H(p)
     */
    int calculateXH(int[] p) {
        if (p.length != 9)
            return 0;
        // calculate crossing number xH
        int xH = 0;
        for (int i = 1; i <= 4; i++) {
            if ( (p[2*i - 1] == 0) && ( p[2*i] == 1 || p[ 2*i % 8 + 1] == 1) ) {
                xH++;
            }
        }
        return xH;
    }

}
