package Algorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import SVG.*;
import javafx.scene.image.ImageView;


/**
 * Implements the whole ScanLine Process.
 * Returns the resulting vector Image in BufferedImage format.
 * getSVG() returns SVG Object in String format.
 *
 * Parameters:
 * lineThickness: threshold for line/region differentiation
 * regionHandling: [ region, contourAsLine, fillWithLines, divideLines ]
 */
public class ScanLine implements iImageAlgorithm {


    // main attributes
    private BufferedImage image;
    private int[][] array; // image as array of 0s and 1s
    private int[][] distanceMatrix;
    SVG svg;

    // Parameters
    private int lineThickness = 3;
    private int regionHandling = 0;

    //Setters for Parameters

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }


    public void setRegionHandling(int regionHandling) {
        this.regionHandling = regionHandling;
    }

    // Constructors
    public ScanLine() {

    }

    public ScanLine(int lineThickness) {
        this.lineThickness = lineThickness;

    }


    /**
     * calculates the DistanceMatrix of the black&white BufferedImage stored in image.
     */
    private void calculateDistanceMatrix() {
        iArrayAlgorithm calculateArray = new ConvertImageToArray( image, 128 ); // threshold hardcoded, because source image should be black and white
        array = calculateArray.processArray( null );

        iArrayAlgorithm calculateDistanceMatrix = new CalculateDistanceMatrix();
        this.distanceMatrix = calculateDistanceMatrix.processArray( array );
    }

    /**
     * calculates the vector image.
     * stores SVG Data in ???
     * returns BufferedImage as representation of the result.
     *
     * @param image black & white image to be processed.
     * @return
     */
    public BufferedImage processImage(BufferedImage image) {

        this.image = image;

        System.out.println( (new Point( 110, 10)).distanceFromLine( (new Point(10,10)), (new Point( 110, 110))));

        calculateDistanceMatrix();

        // First Step: REGIONS

        switch (regionHandling) {
            // leave as region
            case 0:     leaveAsRegion();
                        break;
            case 1:     contourAsLine();
                        break;
            case 2:     fillWithLines();
                        break;
            case 3:     divideAsLines();
                        break;
        }

        // Second Step:
        // skeletonize image (iArrayAlgorithm)

        // Third Step:
        // searchForLines()
        // store in svg

        // Fourth Step:
        // draw new image according to svg information
        // return it

        return this.image;
    }


    // Methods which handle the different kinds of region handling
    // the methods should delete the regions from the original picture
    // and put their SVG Information in attribute svg.
    // --------------------------------------------------------------------------------------------------

    private void leaveAsRegion() {

        int[][] contourOfRegions;
        int[][] withoutRegions;



        /* iArrayAlgorithm invert = new InvertArray();
        distanceMatrix = invert.processArray( distanceMatrix ); */

        iArrayAlgorithm separate = new RemoveRegions( 4 );
        withoutRegions = separate.processArray( distanceMatrix );

        iArrayAlgorithm contour = new GetCountourOfRegions( 8 );
        contourOfRegions = contour.processArray( distanceMatrix );

        iSVGAlgorithm search = new SearchLine();
        SVG contourSVG = search.processArray( contourOfRegions );
        SVG restSVG = search.processArray( withoutRegions );

//        contourSVG.addSVG( restSVG );

        System.out.println( contourSVG.getFile() );

        contourSVG.deleteLinesShorterThan( 2 );

        System.out.println( contourSVG.getFile() );



        this.image = contourSVG.getImage( this.image.getWidth(), this.image.getHeight());


        /*iImageAlgorithm convertToImage = new ConvertDistanceMatrixToImage( withoutRegions );
        this.image = convertToImage.processImage( null );*/

    }


    private void contourAsLine() {

    }

    private void fillWithLines() {

    }

    private void divideAsLines() {

    }

    /**
     * returns combined array of the two input arrays (|)
     * (dont yet know where to put it)
     */
    private int[][] combine ( int[][] a1, int[][] a2) {

        int width = a1.length;
        int height = a1[0].length;

        int[][] c = new int[width][height];

        try {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++) {
                    if ( (a1[i][j] > 0) || (a2[i][j] > 0) ) {
                        c[i][j] = 1;
                    }
                }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(" Combine: Arrays of different size!");
        }

        return c;
    }


}
