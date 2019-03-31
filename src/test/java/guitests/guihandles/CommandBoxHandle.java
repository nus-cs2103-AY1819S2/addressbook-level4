package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

import seedu.address.ui.AutoCompleteTextField;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle<AutoCompleteTextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(AutoCompleteTextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();
        guiRobot.type(KeyCode.ENTER);
    }


    /**
     * Enters the given prefix and selects from the autocompletion context menu.
     */
    public void selectFromAutoComplete(String prefix) {
        click();
        guiRobot.interact(() -> getRootNode().setText(prefix));
        guiRobot.interact(() -> getRootNode().positionCaret(getInput().length()));
        // to ensure that the selection does not get stuck
        for (int i = 0; i < getInput().length(); i++) {
            guiRobot.type(KeyCode.BACK_SPACE);
        }
        guiRobot.interact(() -> getRootNode().setText(prefix));
        guiRobot.pauseForHuman();
        guiRobot.type(KeyCode.DOWN);
        guiRobot.type(KeyCode.ENTER);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
