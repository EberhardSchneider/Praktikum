package GUI;

import SVG.*;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.ImageView;
import Algorithm.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
        ivImage.setFitHeight(800);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("linefill_param.fxml"));
            Parent page = loader.load();

            LinefillParamController controller = loader.getController();


            Stage dialogStage = new Stage();

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Integer x = -20000;


            dialogStage.showAndWait();


            iImageAlgorithm fillAlgorithm = null;

            switch ((int)controller.getFill()) {
                case 0: // Scanline
                    System.out.println("Scanline!");
                    int direction = controller.getLineDirection();
                    switch (direction) {
                        case 0:
                            System.out.println("Horizontal!");

                            fillAlgorithm = new FillHorizontal(controller.getNumberOfGrayLevels());
                            break;
                        case 1:
                            System.out.println("vertical!");

                            fillAlgorithm = new FillVertical(controller.getNumberOfGrayLevels());
                            break;
                        case 2:
                            System.out.println("spiral!");

                            fillAlgorithm = new FillImageSpiral(controller.getNumberOfGrayLevels());
                            break;
                    }
                    break;
                case 1: // Fillhilbert
                    System.out.println("Fillhilbert!");

                    int minIterations = controller.getMinIterations();
                    int maxIterations = controller.getMaxIterations();
                    fillAlgorithm = new FillHilbert(minIterations, maxIterations);

            }


                ///File f = new File("C:\\Users\\eberh_000\\tiger.png");
                ///BufferedImage image = ImageIO.read(f);

                MakeGrayScale gray = new MakeGrayScale();
                RemoveAlpha ra = new RemoveAlpha();




                workflow.setAlgorithm(ra);
                workflow.doAction();


                workflow.setAlgorithm(gray);
                workflow.doAction();

                workflow.setAlgorithm(fillAlgorithm);
                workflow.doAction();



        } catch (IOException e) {
            e.printStackTrace();
        }




        showImage();

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

        workflow.setAlgorithm( new MakeBlackWhite( 100) );
        workflow.doAction();


        Slider slider = new Slider();
        slider.setId("bw_threshold");
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

        iImageAlgorithm scanline = new ScanLine( );

        workflow.setAlgorithm( scanline );
        workflow.doAction();

        showImage();
    }

    public void svgTest() {
        ivImage.setFitHeight(800);

        BufferedImage image = new BufferedImage(50,50,BufferedImage.TYPE_BYTE_GRAY);

        iImageAlgorithm a = new TestBresenham( ivImage );
        a.processImage(image);
    }
}