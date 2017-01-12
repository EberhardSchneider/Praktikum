package GUI;

import Vector.VectorImage;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.ImageView;
import Algorithm.*;
import StateAlgorithm.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class of Main GUI.
 * Implements main functionality.
 */
public class Controller {


    @FXML
    Button button_load;
    @FXML
    Button button_linefill;
    @FXML
    Button button_scanline;
    @FXML
    CheckBox checkbox_size;
    @FXML
    CheckBox checkbox_showvector;
    @FXML
    CheckBox checkbox_showimage;
    @FXML
    VBox vboxParam;


    /**
     * Holds the states with images and algorithms to  process them.
     */
    Command workflow = new Command();

    /**
     * Holds the current imageData (could be retrieved from workflow)
     */
    ImageData imageData;



    @FXML
    ImageView ivImage;
    @FXML
    ImageView ivVector;
    @FXML
    GridPane gridpaneMain;







    /**
     * Opens a dialogue for the Linefill operation,
     * then proceeds with the processing.
     *
     * Gets called when #buttonLinefill is clicked.
     */

    @FXML
    public void buttonLinefillClick() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("linefill_param.fxml"));
            Parent page = loader.load();

            // get FXML Controller, to retrieve the parameters from GUI later
            LinefillParamController controller = loader.getController();

            // open dialog
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Integer x = -20000;

            dialogStage.showAndWait();

            // retrieve algorithm, and its parameters from GUI
            iStateAlgorithm fillAlgorithm = null;

            switch ((int)controller.getFill()) {
                case 0: // Scanline
                    System.out.println("Scanline!");
                    int direction = controller.getLineDirection();
                    switch (direction) {
                        case 0: // horizontal
                            fillAlgorithm = new StateFillHorizontal(controller.getNumberOfGrayLevels());
                            break;
                        case 1: // vertical
                            //fillAlgorithm = new FillVertical(controller.getNumberOfGrayLevels());
                            break;
                        case 2: // spiral
                            //fillAlgorithm = new FillImageSpiral(controller.getNumberOfGrayLevels());
                            break;
                    }
                    break;
                case 1: // Fillhilbert

                    int minIterations = controller.getMinIterations();
                    int maxIterations = controller.getMaxIterations();
                    System.out.println( "Min:" + minIterations + "  Max:  " + maxIterations );
                    // fillAlgorithm = new FillHilbert(minIterations, maxIterations);
            }


                ///File f = new File("C:\\Users\\eberh_000\\tiger.png");
                ///BufferedImage image = ImageIO.read(f);

                // in every case:
                // 1. transform image in grayscale format
                // 2. remove alpha channel

                StateMakeGrayscale gray = new StateMakeGrayscale();
                StateRemoveAlpha ra = new StateRemoveAlpha();

                workflow.setAlgorithm(ra);
                workflow.doAction();

                workflow.setAlgorithm(gray);
                workflow.doAction();

                // now use the chosen algorithm

                workflow.setAlgorithm(fillAlgorithm);
                workflow.doAction();

                showImage();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads an image file and starts a new command workflow with the image in its first state.
     * Is called when the #buttonLoad Button is clicked.
     */
    @FXML
    public void buttonLoadClick() {

        // create file dialog
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
            button_load.setVisible(false);
        }
        catch (IOException e) {
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

    @FXML
    public void sizeAction() {
        showImage();
    }

    @FXML
    public void showVectorAction() {
        ivVector.setVisible( checkbox_showvector.isSelected());
    }

    @FXML
    public void showImageAction() {
        ivImage.setVisible( checkbox_showimage.isSelected() );
    }

    /**
     * Shows the image in the current workflow state in the GUI.
     *
     */
    void showImage() {
        int width, height;

        BufferedImage image = workflow.getImage();
        BufferedImage imageVector = null;
        VectorImage vectorImage = workflow.getVectorImage();

        Boolean sizing = checkbox_size.isSelected();
        Boolean showVector = checkbox_showvector.isSelected();
        Boolean showImage = checkbox_showimage.isSelected();


        Image fxImage = SwingFXUtils.toFXImage(image, null);
        ivImage.setImage(fxImage);



        if (vectorImage != null) {
            imageVector = vectorImage.getImage(5f);
            System.out.println("Draws In Image");
        }
        if (imageVector != null)
            ivVector.setImage( SwingFXUtils.toFXImage( imageVector, null ));



        if (sizing) {
            ivImage.setFitHeight(0);
            ivImage.setFitWidth(0);
            ivVector.setFitWidth(0);
            ivVector.setFitHeight(0);
        } else
        {
            ivImage.setFitHeight(500);
            ivImage.setFitWidth(900);
            ivVector.setFitHeight(500);
            ivVector.setFitWidth(900);
        }



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

        workflow.setAlgorithm(new StateRemoveAlpha());
        workflow.doAction();

        workflow.setAlgorithm(new StateMakeGrayscale());
        workflow.doAction();

        //workflow.setAlgorithm( new MakeBlackWhite( 100) );
        //workflow.doAction();


        Slider slider = new Slider();
        slider.setId("bw_threshold");
        slider.setMin(1);
        slider.setMax(255);
        slider.setValue(180);
        slider.setShowTickLabels(true);
        slider.setOrientation(Orientation.VERTICAL);


        slider.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if ( oldValue == newValue ) return;
                workflow.undoAction();

                workflow.setAlgorithm( new MakeBlackWhite( (int)Math.round(newValue.doubleValue()) ) );
                workflow.doAction();

                showImage();
            }
        });

        Button button = new Button("Proceed");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // remove slider and Proceed button
                vboxParam.getChildren().remove( vboxParam.getScene().lookup("#bw_threshold") );
                vboxParam.getChildren().remove(event.getSource());

                // continue with scanline process
                scanlineTWO();
                showImage();
            }
        });


        vboxParam.getChildren().add( slider );
        vboxParam.getChildren().add( button );

        showImage();
    }

    public void scanlineTWO() {

        ScanLine scanline = new ScanLine( );
        //for testing:
        scanline.setImageView( ivImage );

        //workflow.setAlgorithm( scanline );
        //workflow.doAction();

        showImage();
    }

    public void svgTest() {
        ivImage.setFitHeight(800);

        BufferedImage image = new BufferedImage(50,50,BufferedImage.TYPE_BYTE_GRAY);

        iImageAlgorithm a = new TestBresenham( ivImage );
        a.processImage(image);
    }
}