package GUI;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by eberh_000 on 24.10.2016.
 */
public class LinefillParamController {

    Stage stage;

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




    // Constructor (sets Stage, so we can close the window properly)
    public LinefillParamController() {
        this.stage = stage;
    }



}
