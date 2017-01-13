package StateAlgorithm;

import Algorithm.*;
import GUI.ImageData;
import GUI.State;
import Vector.VectorImage;

import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 12.01.2017.
 */
public class StateScanline implements iStateAlgorithm {

    // main attributes
    private BufferedImage image;
    private int[][] array; // image as array of 0s and 1s
    private int[][] distanceMatrix;
    VectorImage vectorImage;

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
    public StateScanline() {

    }

    public StateScanline(int lineThickness) {
        this.lineThickness = lineThickness;

    }


    /**
     * calculates the DistanceMatrix of the black&white BufferedImage stored in image.
     */


    /**
     * calculates the vector image.
     * stores Vector Data in ???
     * returns BufferedImage as representation of the result.
     *
     * @param imageData black & white image to be processed.
     * @return
     */
    public State processImage(ImageData imageData) {

        this.image = imageData.getImage();
        array = imageData.getImageArray();

        iArrayAlgorithm calculateDistanceMatrix = new CalculateDistanceMatrix();
        this.distanceMatrix = calculateDistanceMatrix.processArray( array );

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
        // store in vectorImage

        // Fourth Step:
        // draw new image according to vectorImage information
        // return it

        return new State(image);
    }


    // Methods which handle the different kinds of region handling
    // the methods should delete the regions from the original picture
    // and put their Vector Information in attribute vectorImage.
    // --------------------------------------------------------------------------------------------------

    private void leaveAsRegion() {

        int[][] contourOfRegions;
        int[][] withoutRegions;



        /* iArrayAlgorithm invert = new InvertArray();
        distanceMatrix = invert.processArray( distanceMatrix ); */

        // iArrayAlgorithm separate = new RemoveRegions( 5 );
        // withoutRegions = separate.processArray( distanceMatrix );

        //iArrayAlgorithm contour = new GetCountourOfRegions( 8 );
        //contourOfRegions = contour.processArray( distanceMatrix );

        iArrayAlgorithm skelet = new SkeletonizeMatrix();
        int[][] s = skelet.processArray( array );

        iVectorAlgorithm fLine = new FindLine();
        VectorImage result = fLine.processArray( s );

        image = result.getImage( s.length, s[0].length );
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
