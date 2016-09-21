package Algorithm;

import javafx.scene.image.Image;

/**
 * This schould be implemented by all classes, which process Image informations.
 */
abstract class Algorithm {
    private Image image;

    // Setter Method for the image field
    public void setImage(Image i) {
        image = i;
    }

    // Public Method to run the algorithm and get the processed Image.
    public Image getProcessedImage() {
        Image processedImage = new Image();
        processedImage = processImage();
        return processedImage;
    }

    abstract Image processImage();
}
