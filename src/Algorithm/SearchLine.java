package Algorithm;

import Vector.*;

import java.util.ArrayList;

/**
 * Now it's getting serious!
 */
public class SearchLine implements iSVGAlgorithm {

    VectorImage vectorImage = new VectorImage();

    int width;
    int height;

    final static class Polyline {
        ArrayList<Point> polyline = new ArrayList<>();
    }



    public VectorImage processArray(int[][] array ) {

        width = array.length;
        height = array[0].length;

        ArrayList<Polyline> polylines = new ArrayList<>();

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

                    Polyline currentPolyline = new Polyline();

                    Point pixel = new Point(x, y);

                    // start polyline
                    currentPolyline.polyline.add( pixel );

                    visitedPixels.add( pixel );
                    Point startingPoint = pixel;
                    Point firstNeighbour = pixel.getFirstNeighbour( array );
                    visitedPixels.add( firstNeighbour);


                    while (firstNeighbour != null) {


                        // Calculate Bresenham-Line from fist pixel in array to last found pixel
                        ArrayList<Point> idealLine = startingPoint.bresenham( firstNeighbour );

                        // if difference between bresenham-Line and visited Pixels is too great
                        if ( Point.difference( idealLine, visitedPixels ) > 1) {
                            System.out.println("Difference too great!");

                            Point endPoint = visitedPixels.get(visitedPixels.size() - 2);

                            // add next line to Polyline Object
                            currentPolyline.polyline.add( endPoint );

                            // delete all visited pixels from original array
                            /* for (Point p : visitedPixels) {
                                array[p.x][p.y] = 0;
                            }*/
                            // new starting point, delete visitedPixels


                            pixel = visitedPixels.get( visitedPixels.size() - 1);
                            startingPoint = pixel;
                            visitedPixels.clear();
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
                    // now we ended in a dead end... first neighbour = null

                    // first start new polyline
                    currentPolyline.polyline.add( pixel );
                    polylines.add( currentPolyline );
                    Point endPoint = pixel;
                    vectorImage.addLine( startingPoint.x, startingPoint.y, endPoint.x, endPoint.y );

                    // clear visitedPixel List
                    visitedPixels.clear();
                    System.out.println("First neighbour null:");

                }
            }

            int index = 0;
        // Algorithm to smooth polylines
        for (Polyline poly : polylines) {
            Point[] polyline = new Point[ poly.polyline.size() ];
            for (int i = 0; i < polyline.length; i++) {
                polyline[i] = poly.polyline.get( i );
            }
            dp(0, polyline.length-1, polyline, vectorImage);
        }

        return vectorImage;

    }

    public void dp( int index1, int index2, Point[] points, VectorImage resultingVectorImage) {
        if ( (index2 - index1) == 1 ) {
            resultingVectorImage.addLine( points[index1].x, points[index1].y, points[index2].x, points[index2].y );
            return;
        }
        Point p1 = points[index1];
        Point p2 = points[index2];

        float maxDistance = 0;
        int maxIndex = -1;

        for (int i = index1 + 1; i < index2 - 1; i++) {
            float d = points[i].distanceFromLine( p1, p2 );
            if (d > maxDistance ) {
                maxDistance = d;
                maxIndex = i;
            }
        }

        if (maxDistance < 1) {
            // add line from index1 to index2
            resultingVectorImage.addLine( points[index1].x, points[index1].y, points[index2].x, points[index2].y );
            return;
        }

        dp( index1, maxIndex, points, resultingVectorImage);
        dp( maxIndex, index2, points, resultingVectorImage);
    }





}
