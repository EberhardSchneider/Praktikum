package Algorithm;

import SVG.*;


import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by eberh_000 on 25.11.2016.
 */
public class FindLine implements iSVGAlgorithm {

    int[][] array;      // image-Array

    int width;
    int height;         // of image-Array

    // for the moment i store the data in these datastructures
    class Line {
        Point start, end;
        public Line( Point p1, Point p2) {
            this.start = p1;
            this.end = p2;
        }
    }

    class Polyline {
        ArrayList<Point> polyline;
    }

    ArrayList<ArrayList<Point>> imageLineData = new ArrayList<>();

    @Override
    public SVG processArray(int[][] a) {

        this.array = a;
        this.width = a.length;
        this.height = a[0].length;

        // 1. go through array line for line and look for first black pixel
        // 2. if blackpixel is found:
        //          3. store pixel in currentPixel, begin new LINE, set direction = -1
        //          4. search for neightbour pixel = new currentPixel, if there is one:
        //                  5. if size(LINE) < 2:  LINE.END = currentPixel; set direction
        //                  6. else: check if newDirection = direction:
        //                          7. yes: LINE.END = currentPixel
        //                          8. no: store LINE in POLYLINE, begin new Line: -> 3.
        //          9. if there is no pixel in neighbourhood: store POLYLINE, -> 1.


        Point currentPixel;
        Line currentLine;


        int direction;

        // 1. go through array line for line and look for first black pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // 2. if blackpixel is found:
                if (array[x][y] == 1) {

                    ArrayList<Point> currentPolyline = new ArrayList<>();

                    //  3. store pixel in currentPixel, begin new LINE, set direction = -1
                    currentPixel = new Point( x, y);
                    array[x][y] = 0;   // delete pixel in original image

                    currentLine = new Line( currentPixel, null );
                    currentPolyline.add( currentPixel ); // add starting point to poyline
                    direction = -1;

                    // 4. search for neightbour pixel = new currentPixel, if there is one:
                    Point neighbour = currentPixel.getFirstNeighbour( array );
                    while (neighbour != null) {

                        // 5. if size(LINE) < 2:  LINE.END = currentPixel; set direction
                        if (currentLine.end == null) {
                            currentPixel = neighbour;
                            currentLine.end = currentPixel;
                            array[currentPixel.x][currentPixel.y] = 0;  // delete pixel in original image

                            direction = currentLine.start.getDirection( currentPixel );
                        }

                        //6. else: check if newDirection = direction:
                        else {
                            if (currentPixel.getDirection( neighbour ) == direction) {

                                // 7. yes: LINE.END = currentPixel
                                currentPixel = neighbour;
                                currentLine.end = currentPixel;
                                array[currentPixel.x][currentPixel.y] = 0;  // delete pixel in original image
                            }



                                // 8. no: store LINE in POLYLINE, begin new Line: -> 3.
                                currentPolyline.add( currentPixel );
                                currentLine = null;
                                currentPixel = null;

                        }

                        if (currentPixel != null) {
                            neighbour = currentPixel.getFirstNeighbour( array );
                        }
                        else {
                            neighbour = null;
                        }

                    } // while neighbour != null



                        currentPolyline.add( currentPixel );
                        imageLineData.add( currentPolyline );


                }
            }
        }

        SVG resultingSVG = new SVG();

        // Algorithm to smooth polylines
        for (ArrayList<Point> poly : imageLineData) {
            Point[] polyline = new Point[ poly.size() ];
            for (int i = 0; i < polyline.length; i++) {
                polyline[i] = poly.get( i );
            }
            dp(0, polyline.length-1, polyline, resultingSVG);
        }



        return resultingSVG;
    }

    public void dp( int index1, int index2, Point[] points, SVG resultingSVG) {
        if ( (index2 - index1) == 1 ) {
            resultingSVG.addLine( points[index1].x, points[index1].y, points[index2].x, points[index2].y );
            return;
        }
        Point p1 = points[index1];
        Point p2 = points[index2];

        float maxDistance = 0;
        int maxIndex = -1;

        for (int i = index1 + 1; i < index2 - 1; i++) {
            System.out.println( i );
            float d = points[i].distanceFromLine( p1, p2 );
            if (d > maxDistance ) {
                maxDistance = d;
                maxIndex = i;
            }
        }

        if (maxDistance < 1) {
            // add line from index1 to index2
            resultingSVG.addLine( points[index1].x, points[index1].y, points[index2].x, points[index2].y );
            return;
        }

        dp( index1, maxIndex, points, resultingSVG);
        dp( maxIndex, index2, points, resultingSVG);
    }

}
