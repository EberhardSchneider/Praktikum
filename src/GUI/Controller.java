package GUI;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.scene.image.ImageView;
import Algorithm.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * Created by eberh_000 on 21.09.2016.
 */
public class Controller {




    @FXML
    Button button_linefill;
    @FXML
    Button button_scanline;
    @FXML
    VBox vboxParam;


    Command workflow = new Command();



    @FXML
    ImageView ivImage;
    GridPane gridpaneMain;







    /**
     * Should open a dialogue for the Linefill Parameters,
     * then proceed with the processing.
     * In the Moment it just does a Linefill with set parameters.
     * Gets called when #buttonLinefill is clicked.
     */

    @FXML
    public void buttonLinefillClick() {
        ivImage.setFitHeight(400);
        try {

            ///File f = new File("C:\\Users\\eberh_000\\tiger.png");
            ///BufferedImage image = ImageIO.read(f);

            MakeGrayScale gray = new MakeGrayScale();
            RemoveAlpha ra = new RemoveAlpha();
            FillHilbert fh = new FillHilbert();








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

    /**
     * Loads an image file and starts a new command workflow with the image in its first state.
     * Gets called when the #buttonLoad Button is clicked.
     */
    @FXML
    public void buttonLoadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Image");
        fileChooser.setInitialDirectory(new File("C:\\Users\\eberh_000"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );



        try {
            // Open File Dialog and reset workflow to new image
            File f = fileChooser.showOpenDialog(ivImage.getScene().getWindow());
            BufferedImage image = ImageIO.read(f);
            workflow.initState( image );

            // enable the linefill and scanline button
            button_linefill.setDisable( false );
            button_scanline.setDisable( false );

            showImage();
        }
        catch (Exception e) {
             System.out.println("Error in Load File Dialog");
             e.printStackTrace();
        }

    }

    /**
     * Undos last processing and shows the resulting image.
     * Gets called when #buttonUndo is clicked.
     */
    @FXML
    public void buttonUndoClick() {
        workflow.undoAction();
        showImage();
    }

    /**
     * Shows the image in the current workflow state in the GUI.
     *
     */
    void showImage() {
        Image showImage = SwingFXUtils.toFXImage(workflow.getImage(), null);
        ivImage.setImage(showImage);
    }


    public void buttonScanlineClick() {
      /*  workflow.setAlgorithm( new MakeBlackWhite(200) );
        workflow.doAction();

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(255);
        slider.setValue(180);
        slider.setShowTickLabels(true);

        Button button = new Button("Do It!");
        button.addEventHandler();


        });

        showImage();*/
    }
}