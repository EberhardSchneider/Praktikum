package Algorithm;

import SVG.*;

/**
 * Now it's getting serious!
 */
public class SearchLine implements iSVGAlgorithm {

    SVG svg = new SVG();

    public SVG processArray( int[][] array ) {



        /*
        1: go through array and find first pixel = 1
        2: store pixel in list <visitedPixels>
        search next point, delete it, compare line from this point to starting point
        with pixels in list... if the difference is too great, add line from starting point
        to last pixel in list... delete pixels in list from original array
        3: continue from last found pixel with 2: until no neighbour is reachable
            then continue with 1 until last pixel of image is reached
        */

    }





}
