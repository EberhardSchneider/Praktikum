package Algorithm;

import java.awt.image.BufferedImage;

/**
 * Implements the whole ScanLine Process.
 * Returns the resulting vector Image in BufferedImage format.
 * getSVG() return SVG File.
 *
 * Parameters:
 * lineThickness: threshold for line-region differentiation
 * regionHandling: [ region, contourAsLine, fillWithLines, divideLines ]
 */
public class ScanLine implements iImageAlgorithm {

    // main attributes
    private BufferedImage image;
    private int[][] distanceMatrix;

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
        calculateDistanceMatrix();
    }

    public ScanLine(int lineThickness) {
        this.lineThickness = lineThickness;
        calculateDistanceMatrix();
    }


    /**
     * calculates the DistanceMatrix of the black&white BufferedImage stored in image.
     */
    private void calculateDistanceMatrix() {
        iArrayAlgorithm calculateArray = new ConvertImageToArray( image, 128 ); // threshold hardcoded, because source image should be black and white
        iArrayAlgorithm calculateDistanceMatrix = new CalculateDistanceMatrix();

        this.distanceMatrix = calculateDistanceMatrix.processArray( calculateArray.processArray( null ) );
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
        // store where???

        return null;
    }
}
