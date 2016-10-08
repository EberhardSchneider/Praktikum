package Matrix;

import Algorithm.*;
import java.awt.image.BufferedImage;

/**
 * Created by eberh_000 on 07.10.2016.
 */
public class DistanceMatrix {

    private int[][] array;

    private int width;
    private int height;

    private int maxValue;
    private int minValue;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public DistanceMatrix(BufferedImage image, int threshold) {
        iMatrixAlgorithm convert = new ConvertImageToMatrix( image, threshold);
        DistanceMatrix matrix = convert.processMatrix( null );

        calculateMinMax();
    }

    public DistanceMatrix(int[][] array) {
        this.array = array;
        calculateMinMax();
    }

    public DistanceMatrix(int x, int y) {
        int[][] a = new int[x][y];

        this.array = a;

        width = x;
        height = y;
        maxValue = 0;
        minValue = 0;
    }

    public int getValue(int x, int y) {
        if ( (x >= 0) && (y >= 0) && (x < width) && (x < height) )
            return array[x][y];
        return 0;
    }

    public void setValue(int x, int y, int value) {
        array[x][y] = value;
    }

    private void calculateMinMax() {

        this.width = array.length;
        this.height = array[0].length;

        int min = 255;
        int max = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (array[x][y] > max)
                    max = array[x][y];
                if (array[x][y] < min) {
                    min = array[x][y];
                }
            }

        this.minValue = min;
        this.maxValue = max;
    }


}
