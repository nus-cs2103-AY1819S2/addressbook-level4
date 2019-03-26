package seedu.finance.logic.commands;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;

/**
 * Gives user the summary of all expenses in a graph format
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    //May want to edit later if want to be able to set summary according to a specific duration
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the summary of all your expenses";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Summary command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
