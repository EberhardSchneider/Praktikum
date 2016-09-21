package Algorithm;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

/**
 * Created by eberh_000 on 21.09.2016.
 */
public class MakeGrayScale implements Algorithm {
    @Override
    Image processImage() {

        double width = image.getWidth();
        double height = image.getHeight();

        Image result = new Image(width, height);

        PixelReader pr = new PixelReader( image );
        PixelWriter pw = new PixelWriter( image );

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int rgb = pr.getColor( x, y);
                rgb  /= 2;

            }
    }
}
