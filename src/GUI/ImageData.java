package GUI;

import Vector.VectorImage;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Created by eberh_000 on 28.11.2016.
 */
public class ImageData {
    BufferedImage image;
    int[][] imageArray;
    VectorImage vectorImage;

    public ImageData(BufferedImage i, int[][] iArray, VectorImage vImage) {
        this.image = i;
        this.imageArray = iArray;
        this.vectorImage = vImage;
    }

    public BufferedImage getImage() { return image; }
    public int[][] getImageArray() { return imageArray; };
    public VectorImage getVectorImage() { return vectorImage; }

}
