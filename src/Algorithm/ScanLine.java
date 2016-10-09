package Algorithm;

import java.awt.image.BufferedImage;
import SVG.*;

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

        //iArrayAlgorithm separate = new RemoveRegions( 10 );
        //distanceMatrix = separate.processArray( distanceMatrix );
        iImageAlgorithm convertToImage = new ConvertDistanceMatrixToImage( distanceMatrix );

        this.image = convertToImage.processImage( null );
    }


    private void contourAsLine() {

    }

    private void fillWithLines() {

    }

    private void divideAsLines() {

    }
}
