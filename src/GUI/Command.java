package GUI;

import java.awt.image.BufferedImage;
import Algorithm.iAlgorithm;


import java.util.ArrayList;

/**
 * Holds a list of States to implement do/undo functionality.
 * (Kind of implements Command Pattern.)
 */
public class Command {

    // stateFlow holds all states of the image processing
    private ArrayList<State> stateFlow = new ArrayList<>();
    private State currentState = null;

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
    }

    public BufferedImage getImage() {
        return currentState.image;
    }

    public void setAlgorithm(iAlgorithm a) {
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

    }

    public void undoAction() {
        int index = stateFlow.indexOf( currentState );
        if ( index == 0) return;

        index--;
        currentState = stateFlow.get( index );
    }
}
