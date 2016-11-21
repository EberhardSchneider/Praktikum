package Algorithm;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by eberh_000 on 20.10.2016.
 */
public class TestBresenham implements iImageAlgorithm {

    ImageView imageView;
    BufferedImage image;


    public TestBresenham(ImageView iv) {
        this.imageView = iv;

    }

    void showImage() {
        Image showImage = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(showImage);
    }

    public BufferedImage processImage(BufferedImage image) {
        Point start = new Point(5, 5);
        this.image = image;

        ArrayList<Point> list = bresenham( new Point(5,5), new Point(5,40));


        return image;
    }

    public ArrayList<Point> bresenham(Point start, Point target  ) {


        Graphics2D g = image.createGraphics();
        ArrayList<Point> line = new ArrayList<>();

        int x0 = start.x;
        int y0 = start.y;

        int x1 = target.x;
        int y1 = target.y;

        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx+dy;



        while ( (x0 != x1) || (y0 != y1) ) {
            line.add( new Point( x0, y0) );
            image.setRGB( x0, y0, 0xFFFFFF);
            showImage();
            int e2 = 2 * err;
            if (e2 > dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        // now add point x1, y1
        line.add( new Point( x0, y0) );
        image.setRGB( x0, y0, 0xFFFFFF);
        showImage();

        return line;
    }
}
