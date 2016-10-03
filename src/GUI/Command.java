package GUI;

import java.awt.image.BufferedImage;
import Algorithm.iImageAlgorithm;


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
    // does nothing in the moment, first state is added when image file is loaded.
    public Command() {
    }

    /**
     * Creates a new workflow, with a first state.
     * @param image image to be processed
     */
    public void initState(BufferedImage image) {
        stateFlow.clear();
        State state = new State(image, null);
        stateFlow.add( state );
        currentState = state;
        index = 0;
    }

    public BufferedImage getImage() {
        return currentState.image;
    }

    public void setAlgorithm(iImageAlgorithm a) {
        currentState.algorithm = a;
    }


    public void doAction() {
        if (currentState == null || currentState.algorithm == null
                || currentState.image == null) {
            return;
        }


        BufferedImage newImage = currentState.algorithm.processImage(currentState.image);
        State newState = new State( newImage, null);
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
