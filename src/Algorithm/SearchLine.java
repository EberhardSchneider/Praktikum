package Algorithm;

import SVG.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Now it's getting serious!
 */
public class SearchLine implements iSVGAlgorithm {

    SVG svg = new SVG();

    int width;
    int height;

    public SVG processArray( int[][] array ) {

        width = array.length;
        height = array[0].length;



        /*
        1: go through array and find first pixel = 1
        2: store pixel in list <visitedPixels>
        search next point, delete it, compare line from this point to starting point
        with pixels in list... if the difference is too great, add line from starting point
        to last pixel in list... delete pixels in list from original array
        3: continue from last found pixel with 2: until no neighbour is reachable
            then continue with 1 until last pixel of image is reached
        */

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {

                if (array[x][y] != 0) {

                    ArrayList<Point> visitedPixels = new ArrayList<>();

                    Point pixel = new Point(x, y);
                    visitedPixels.add( pixel );
                    Point startingPoint = pixel;

                    Point firstNeighbour = pixel.getFirstNeighbour( array );

                    while (firstNeighbour != null) {
                        // Calculate Bresenham-Line from fist pixel in array to last found pixel
                        ArrayList<Point> idealLine = startingPoint.bresenham( firstNeighbour );

                        // if difference between bresenham-Line and visited Pixels is too great
                        System.out.println(Point.difference( idealLine, visitedPixels ));
                        if ( Point.difference( idealLine, visitedPixels ) > 0 ) {
                            // add line to svg object
                            Point endPoint = visitedPixels.get(visitedPixels.size() - 1);
                            svg.addLine( startingPoint.x, startingPoint.y, endPoint.x, endPoint.y );
                            // delete all visited pixels from original array
                            for (Point p : visitedPixels) {
                                array[p.x][p.y] = 0;
                            }
                            // new starting point, delete visitedPixels
                            visitedPixels.clear();

                            startingPoint = firstNeighbour;
                            pixel = startingPoint;
                            visitedPixels.add( pixel );

                            firstNeighbour = pixel.getFirstNeighbour( array );
                        }
                        else {
                            visitedPixels.add( firstNeighbour  );
                            array[pixel.x][pixel.y] = 0;
                            pixel = firstNeighbour;
                            firstNeighbour = pixel.getFirstNeighbour( array );
                        }

                    }




                }
            }





        return svg;

    }





}
