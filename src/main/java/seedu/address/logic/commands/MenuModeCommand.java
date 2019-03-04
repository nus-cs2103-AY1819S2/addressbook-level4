package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODE_CHANGE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Change the RestOrRant's mode to {@code Mode.MENU_MODE}.
 * Used to adding, deleting, editing menu items from menu.
 */
public class MenuModeCommand extends ChangeModeCommand {
    public static final String COMMAND_WORD = "menuMode"; // change to standardize with other modes
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change to Menu Mode.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Mode changed to Menu Mode";
    
    @Override
    public CommandResult generateCommandResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, Mode.MENU_MODE);
    }
    
    @Override
    boolean isSameMode(Mode mode) {
        return mode.equals(Mode.MENU_MODE);
    }

}
