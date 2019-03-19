package seedu.address.logic.commands.management;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.modelmanager.Model;

/**
 * Terminates the program.
 */
public class ExitCommand implements Command {
    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting BrainTrain as requested ...";

    /**
     * Executes the command and returns the result message.
     * @param model which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }
}
