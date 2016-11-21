package GUI;

/**
 * class to hold and pass parameters for the linefill algorithm
 */
public class LinefillParameters {

    // Hilbertfill parameters

    int minIterations;
    int maxIterations;




    // Linefill Parameters
    private int lineDirection;

    public static final int LINE_HORIZONTAL = 0;
    public static final int LINE_VERTICAL = 1;
    public static final int LINE_SPIRAL = 2;

    private int numberOfGrayLevels;

    private int scaleFactor;

    public LinefillParameters(int direction, int nog, int scale) {

        direction = (direction  < 0) ? 0 : direction;
        direction = (direction  > 2) ? 2 : direction;

        nog  = (nog < 0)  ? 0 : nog;
        nog  = (nog > 10)  ? 10 : nog;

        this.lineDirection = direction;
        this.numberOfGrayLevels = nog;
        this.scaleFactor = scale;

    }
}
