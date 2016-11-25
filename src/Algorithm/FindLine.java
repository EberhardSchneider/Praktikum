package Algorithm;

import SVG.*;

import java.lang.reflect.Array;

/**
 * Created by eberh_000 on 25.11.2016.
 */
public class FindLine implements iSVGAlgorithm {

    int[][] array;      // image-Array

    int width;
    int height;         // of image-Array




    @Override
    public SVG processArray(int[][] a) {

        this.array = a;
        this.width = a.length;
        this.height = a[0].length;

        // 1. go through array line for line and look for first black pixel
        // 2. if blackpixel is found:
        //          3. store pixel in currentPixel, begin new LINE, set direction = null
        //          4. search for neightbour pixel = new currentPixel, if there is one:
        //                  5. if size(LINE) < 2:  LINE.END = currentPixel; set direction
        //                  6. else: check if newDirection = direction:
        //                          7. yes: LINE.END = currentPixel
        //                          8. no: store LINE in POLYLINE, begin new Line: -> 3.
        //          5. if there is no pixel in neighbourhood: store POLYLINE, -> 1.


        return null;
    }

}
