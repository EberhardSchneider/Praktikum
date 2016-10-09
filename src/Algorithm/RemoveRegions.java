package Algorithm;

import java.util.ArrayList;

/**
 * Created by eberh_000 on 05.10.2016.
 */
public class RemoveRegions implements iArrayAlgorithm {

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
    }

    static final class ListOfPoints extends ArrayList<Point> {

    }

    public RemoveRegions( int l_t ) {
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

        ListOfPoints[] points = new ListOfPoints[line_thickness+1];

        for (int i=0; i < line_thickness+1; i++ ) {
            points[i] = new ListOfPoints();
        }

        // first loop
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if ( (array[x][y] == line_thickness-1) && (hasNeighbourOfValue(x, y, line_thickness)))
                    points[line_thickness].add( new Point(x,y));
                else if ( (array[x][y] >= line_thickness)) {
                    result[x][y] = 1;
                    a[x][y] = 0;
                }
            }

            // loop through all distance values from l_t to 1
        for ( int distanceValue = line_thickness-1; distanceValue > 0; distanceValue-- ) {
            // loop through the previous list
            for (Point p : points[distanceValue + 1]) {
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
                            points[distanceValue].add( new Point( x, y ));
            }
        }

        // now all Points belonging to section should be in lists n -> n-1
        for (int n = line_thickness; n > 0; n--) {
            for (Point p : points[n]) {
                result[p.x][p.y] = 1;
                a[p.x][p.y] = 0;
            }
        }


        return a;

    }


    boolean hasNeighbourOfValue(int xPos, int yPos, int n) {


        int xStart = ( xPos > 0 ) ? xPos-1 : xPos;
        int xEnd = ( xPos < (width-1) ) ? xPos+1 : xPos;
        int yStart = ( yPos > 0 ) ? yPos-1 : yPos;
        int yEnd = ( yPos < (height-1) ) ? yPos+1 : yPos;

        for (int x = xStart; x <= xEnd; x++)
            for (int y = yStart; y <= yEnd; y++)
                if ( (array[x][y] == n) && ( (xPos != x) && (yPos != y) ) )
                    return true;

        return false;

    }

}
