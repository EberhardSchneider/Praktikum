package GUI;

import java.util.ArrayList;

/**
 * Holds a list of States to implement do/undo functionality.
 * (Kind of implements Command Pattern.)
 */
public class Command {

    // stateFlow holds all states of the image processing
    ArrayList<State> stateFlow = new ArrayList<>();
    int currentState = -1;

    // Constructor:
    // does nothing in the moment, first state is added when image file is loaded.
    public Command() {
    }

    public void command_do() {

    }

    public void redo() {
        if (currentState < stateFlow.size()) {
            currentState++;
        }
    }

    public void undo() {

    }
}
