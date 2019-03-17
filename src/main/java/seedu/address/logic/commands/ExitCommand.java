package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.modelmanager.managementmodel.ManagementModel;

/**
 * Terminates the program.
 */
public class ExitCommand implements Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting BrainTrain as requested ...";

    public CommandResult execute(ManagementModel managementModel, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
