package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.scene.image.ImageView;
import Algorithm.*;


/**
 * Created by eberh_000 on 21.09.2016.
 */
public class Controller {

    Command workflow = new Command();


    @FXML
    Button buttonLoad;
    @FXML
    ImageView ivImage;

    @FXML
    public void buttonLoadClick() {
        ivImage.setFitHeight(400);
        try {

            File f = new File("C:\\Users\\eberh_000\\hamster.jpg");
            BufferedImage image = ImageIO.read(f);

            MakeGrayScale gray = new MakeGrayScale();
            RemoveAlpha ra = new RemoveAlpha();
            ScaleDown mr = new ScaleDown(100,100);
            FillHilbert fh = new FillHilbert();



            workflow.initState( image );




            workflow.setAlgorithm(ra);
            workflow.doAction();


            workflow.setAlgorithm(gray);
            workflow.doAction();

            workflow.setAlgorithm(fh);
            workflow.doAction();




            showImage();
        }
        catch (Exception e) {
            System.out.print("Couldn't load file.");
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonUndoClick() {
        workflow.undoAction();
        showImage();
    }

    void showImage() {
        Image showImage = SwingFXUtils.toFXImage(workflow.getImage(), null);
        ivImage.setImage(showImage);
    }
}