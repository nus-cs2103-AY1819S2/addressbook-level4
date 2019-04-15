package seedu.finance.logic.commands;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_ALIAS = "quit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Finance Tracker as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }

}
