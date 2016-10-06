package GUI;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import javafx.scene.image.ImageView;
import Algorithm.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


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
    @FXML
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
        scanlineBlackWhite();
    }


    /**
     * first step of Scanline:
     * makes Slider to transform image to black&white
     * returns when proceed button is pressed
     */
    public void scanlineBlackWhite() {

        workflow.setAlgorithm(new RemoveAlpha());
        workflow.doAction();

        workflow.setAlgorithm(new MakeGrayScale());
        workflow.doAction();

        workflow.setAlgorithm( new MakeBlackWhite( 180) );
        workflow.doAction();


        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(255);
        slider.setValue(180);
        slider.setShowTickLabels(true);
        slider.setOrientation(Orientation.VERTICAL);


        slider.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if ( oldValue == newValue ) return;
                workflow.undoAction();
                System.out.println("Undo");

                workflow.setAlgorithm( new MakeBlackWhite( (int)Math.round(newValue.doubleValue()) ) );
                workflow.doAction();
                System.out.println("Do with threshold = " + (int)Math.round(newValue.doubleValue()));

                showImage();
            }
        });

        Button button = new Button("Proceed");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                vboxParam.getChildren().remove(event.getSource());
                scanlineTWO();
            }
        });


        vboxParam.getChildren().add( slider );
        vboxParam.getChildren().add( button );

        showImage();
    }

    public void scanlineTWO() {
        iArrayAlgorithm convertToArray = new ConvertImageToArray( workflow.getImage(), 180);
        int[][] array = convertToArray.processArray( null );


        iArrayAlgorithm calculateDistanceMatrix = new CalculateDistanceMatrix();
        array = calculateDistanceMatrix.processArray( array );


        iImageAlgorithm convertToImage = new ConvertDistanceMatrixToImage( array );

        workflow.setAlgorithm( convertToImage );
        workflow.doAction();

        showImage();
    }
}