package Algorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Helper class for the Array and Image Algorithms
 */
class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public String toString() {
        return "x: " + x + "  y: " + y;
    }


    /**
     * returns first point with value 1 found in the neighbourhood of Point
     * @param array image array which is processed
     * @return new Point if pixel is found, else null
     */
    public Point getFirstNeighbour(int array[][]) {

        int width = array.length;
        int height = array[0].length;

        int xPos = this.x;
        int yPos = this.y;


        int xStart = xPos > 0 ? xPos-1 : xPos;
        int yStart = yPos > 0 ? yPos-1 : yPos;
        int xEnd = xPos < (width - 1) ? xPos+1 : xPos;
        int yEnd = yPos < (height - 1) ? yPos+1 : yPos;

        for (int x = xStart; x <= xEnd; x++)
            for (int y = yStart; y <= yEnd; y++) {
                if ( (array[x][y] != 0) && ( (xPos != x) || ( yPos != y) ) )
                    return( new Point( x, y) );
            }
        return null;
    }

    public ArrayList<Point> getAllNeighbours(int array[][]) {

        ArrayList<Point> neighbours = new ArrayList<>();

        int width = array.length;
        int height = array[0].length;

        int xPos = this.x;
        int yPos = this.y;


        int xStart = xPos > 0 ? xPos-1 : xPos;
        int yStart = yPos > 0 ? yPos-1 : yPos;
        int xEnd = xPos < (width - 1) ? xPos+1 : xPos;
        int yEnd = yPos < (height - 1) ? yPos+1 : yPos;

        for (int x = xEnd; x >= xStart; x--)
            for (int y = yEnd; y >= yStart; y--) {
                if ( (array[x][y] != 0) && ( (xPos != x) || ( yPos != y) ) )
                    neighbours.add( new Point( x, y) );
            }
        return neighbours;
    }


    /**
     * returns an ArrayList containing the points of the line between
     * Point this and target, calculated by the Bresenham Algorithm
     * @param target
     * @return
     */
    public ArrayList<Point> bresenham( Point target ) {

        ArrayList<Point> line = new ArrayList<>();

        int x0 = this.x;
        int y0 = this.y;

        int x1 = target.x;
        int y1 = target.y;

        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx+dy;



        while ( (x0 != x1) || (y0 != y1) ) {
            line.add( new Point( x0, y0) );
            int e2 = 2 * err;
            if (e2 > dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        // now add point x1, y1
        line.add( new Point( x0, y0) );

        return line;
    }

    public static int difference( ArrayList<Point> list1, ArrayList<Point> list2 ) {

        int difference = 0;

        Iterator it1 = list1.iterator();
        Iterator it2 = list2.iterator();

        while ( it1.hasNext() && it2.hasNext() ) {
            Point p1 = (Point)it1.next();
            Point p2 = (Point)it2.next();

            int dx = Math.abs( p1.x - p2.x );
            int dy = Math.abs( p1.y - p2.y );

            difference += (dx*dx + dy*dy);
        }

        return difference;

    }

    /**
     * returns distance of Point from Line defined by Points p1, p2
     * @param p1 Startpoint of line
     * @param p2 Endpoint of Line
     * @return distance from point to line
     */
    public float distanceFromLine( Point p1, Point p2 ) {
        float distance = Math.abs( (p2.y - p1.y) * this.x - (p2.x - p1.x) * this.y + p2.x * p1.y - p2.y * p1.x);
        distance /= Math.sqrt( Math.pow( (p2.y - p1.y), 2 ) + Math.pow( (p2.x - p1.x ), 2 ) );
        return distance;
    }
}
