package GUI;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by eberh_000 on 24.10.2016.
 */
public class LinefillParamController {

    @FXML TabPane tabpane;

    @FXML Tab tab_linefill;
    @FXML Tab tab_hilbertfill;


    // scanline tab
    @FXML Button button_ok;

    @FXML
    Slider slider_n_graylevels;
    @FXML
    ToggleGroup scanline_direction;

    // hilbertfill tab
    @FXML Slider slider_min_iterations;
    @FXML Slider slider_max_iterations;
    @FXML Label label_min_iterations;
    @FXML Label label_max_iterations;

    // Stores if Linefill or Hilbertfill

    int fill = 0;

    public static final int FILL_SCANLINE = 0;
    public static final int FILL_HILBERT = 1;

    // Hilbertfill parameters

    int minIterations;
    int maxIterations;

    // Linefill parameters

    private int lineDirection;

    public static final int LINE_HORIZONTAL = 0;
    public static final int LINE_VERTICAL = 1;
    public static final int LINE_SPIRAL = 2;

    private int numberOfGrayLevels;

    private int scaleFactor;

    // initalize
    @FXML
    public void initialize() {
        slider_min_iterations.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                minIterations = (int)slider_min_iterations.getValue();
                slider_min_iterations.setValue( minIterations );
                slider_max_iterations.setMin( minIterations );
                label_min_iterations.setText( ((Double)slider_min_iterations.getValue()).toString() );
                label_max_iterations.setText( ((Double)slider_max_iterations.getValue()).toString() );


            }
        });
        slider_max_iterations.valueProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                maxIterations = (int)slider_max_iterations.getValue();
                slider_max_iterations.setValue( maxIterations );
                slider_min_iterations.setMax( maxIterations );
                label_min_iterations.setText( ((Double)slider_min_iterations.getValue()).toString() );
                label_max_iterations.setText( ((Double)slider_max_iterations.getValue()).toString() );

            }
        });
    }





    public void buttonOkPressed(ActionEvent actionEvent) {

        // get information from scanline tab
        this.fill = tabpane.getSelectionModel().getSelectedIndex();
        this.numberOfGrayLevels = (int)slider_n_graylevels.getValue();
        this.lineDirection = Integer.parseInt( (String)scanline_direction.getSelectedToggle().getUserData() );


        // get information from hilbertfill tab


        Stage stage = (Stage)button_ok.getScene().getWindow();
        stage.close();
    }

    public int getFill() {
        return fill;
    }

    public int getLineDirection() {
        return lineDirection;
    }

    public int getNumberOfGrayLevels() {
        return numberOfGrayLevels;
    }

    public int getMinIterations() {
        return minIterations;
    }

    public int getMaxIterations() {
        return maxIterations;
    }
}
