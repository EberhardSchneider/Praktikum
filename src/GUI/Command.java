package GUI;

import java.awt.image.BufferedImage;
import Algorithm.iImageAlgorithm;
import StateAlgorithm.iStateAlgorithm;
import Vector.VectorImage;


import java.util.ArrayList;

/**
 * Holds a list of States and methods to implement do/undo functionality.
 * (Kind of implements Command Pattern.)
 */
public class Command {

    // stateFlow holds all states of the image processing
    private ArrayList<State> stateFlow = new ArrayList<>();
    private State currentState = null;
    private int index;

    // Constructor:
    // does nothing in the moment, first state is added when initState() is called.
    public Command() {
    }

    /**
     * Creates a new workflow, with a first state.
     * @param image image to be processed
     */
    public void initState(BufferedImage image) {
        stateFlow.clear();
        State state = new State(image, null, null);
        stateFlow.add( state );
        currentState = state;
        index = 0;
    }

    public BufferedImage getImage() {
        return currentState.imageData.image;
    }
    public int[][] getImageArray() { return currentState.imageData.imageArray; }
    public VectorImage getVectorImage() { return currentState.imageData.vectorImage; }

    public void setAlgorithm(iStateAlgorithm a) {
        currentState.algorithm = a;
    }

    /**
     * processes the image in current state with the algorithm in current state,
     * then adds new state to state list with the new image and empty algorithm slot.
     */
    public void doAction() {
        if (currentState == null || currentState.algorithm == null
                || currentState.imageData == null) {
            return;
        }


        State newState = currentState.algorithm.processImage( currentState.imageData );
        stateFlow.add( newState );
        currentState = newState;
        index++;

    }

    public void undoAction() {

        if ( index == 0) return;
        index--;

        currentState = stateFlow.get( index );
    }


}
