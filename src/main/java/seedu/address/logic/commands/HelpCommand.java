package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.modelmanager.managementmodel.ManagementModel;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand implements Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public CommandResult execute(ManagementModel managementModel, CommandHistory history) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
