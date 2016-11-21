package GUI;

/**
 * class to hold and pass parameters for the hilbert fill algorithm
 */
public class HilbertfillParameters {
    private int min_iterations;
    private int max_iterations;

    public HilbertfillParameters(int min, int max) {
        this.min_iterations = min;
        this.max_iterations = max;
    }
}
