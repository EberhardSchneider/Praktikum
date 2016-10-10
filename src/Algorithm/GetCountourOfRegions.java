package Algorithm;

import java.util.HashSet;

/**
 * Created by eberh_000 on 10.10.2016.
 */
public class GetCountourOfRegions implements iArrayAlgorithm {
    int[][] array;
    int width;
    int height;
    int line_thickness = 3;

    static final class Point {
        int x;
        int y;
        int brightness;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override public String toString() {
            return "x: " + x + "  y: " + y;
        }
    }

    static final class SetOfPoints extends HashSet<Point> {

    }

    public GetCountourOfRegions( int l_t ) {
        this.line_thickness = l_t;
    }

    public int[][] processArray( int[][] a ) {

        this.array = a;

        width = array.length;
        height = array[0].length;

        int[][] result = new int[width][height];

        // first step:
        // put all pixels with distance value n-1 which have neighbours
        // with distance value n in list l_n
        // then put all pixels with distance value n-2 which have
        // neighbours in list l_n in list l_(n-1) (go through list l_(n-1)
        // continue with n-3 down to 1
        // then delete all pixels in lists and all pixels
        // with distance values >= n

        SetOfPoints region = new SetOfPoints();
        SetOfPoints contour = new SetOfPoints();



        // @TODO: optimization. use different lists for every distance value to avoid looping through all points
        // first loop
        // catch all points with distance value line_thickness-1
        // which have neighbours of line_thickness and all pixels with value higher than line_thickness
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if ( (array[x][y] == line_thickness-1) && (countNeighboursOfValue(x, y, line_thickness) > 0))
                    region.add( new Point(x,y) );
                else if ( (array[x][y] >= line_thickness)) {
                    region.add( new Point( x,y )); // this list contains all points with distance value >= line_thickness
                }
            }


        // loop through all distance values from l_t-1 to 1
        for ( int distanceValue = line_thickness-1; distanceValue > 0; distanceValue-- ) {
            // loop through region and store points in neighbourhood with right distance value
            SetOfPoints newPoints = new SetOfPoints();
            for (Point p : region) {
                // get all Points in neighbourhood with right distance value
                int xPos = p.x;
                int yPos = p.y;

                int xStart = ( xPos > 0 ) ? xPos-1 : xPos;
                int xEnd = ( xPos < (width-1) ) ? xPos+1 : xPos;
                int yStart = ( yPos > 0 ) ? yPos-1 : yPos;
                int yEnd = ( yPos < (height-1) ) ? yPos+1 : yPos;

                for (int x = xStart; x <= xEnd; x++)
                    for (int y = yStart; y <= yEnd; y++)
                        if ( (array[x][y] == distanceValue) && ( (xPos != x) && ( yPos != y) ) )
                            newPoints.add( new Point( x, y ));
            }

            // now merge region with newPoints
            region.addAll( newPoints );
            if (distanceValue == 1)
                contour.addAll( newPoints );
        }


        for (Point p : contour) {
            result[p.x][p.y] = 1;
        }




        return result;

    }


    private int countNeighboursOfValue(int xPos, int yPos, int n) {

        int count = 0;

        int xStart = xPos > 0 ? xPos-1 : xPos;
        int yStart = yPos > 0 ? yPos-1 : yPos;
        int xEnd = xPos < (width - 1) ? xPos+1 : xPos;
        int yEnd = yPos < (height - 1) ? yPos+1 : yPos;

        for (int x = xStart; x <= xEnd; x++)
            for (int y = yStart; y <= yEnd; y++) {
                if ( (array[x][y] == n) && ((x != xPos) && (y != yPos)) ) {
                    count++;
                }
            }
        return count;
    }
}
