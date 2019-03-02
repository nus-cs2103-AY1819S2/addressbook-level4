package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Change the RestOrRant's mode to MenuMode.
 * Used to adding, deleting, editing menu items from menu.
 */
public class MenuMode extends Command {
    public static final String COMMAND_WORD = "menuMode"; // change to standardize with other modes
    public static final String MESSAGE_SUCCESS = "Mode changed to Menu Mode";
    public static final String MESSAGE_FAIL = "Mode is already Menu Mode";
    
    public MainWindow mainWindow;
    
    public CommandResult execute(Mode mode, Model model, CommandHistory history) {
        requireAllNonNull(mode, model);
        
        if (!mode.equals(Mode.MENU_MODE)) {
            mode = Mode.MENU_MODE;
            mainWindow.fillInnerParts(mode);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
