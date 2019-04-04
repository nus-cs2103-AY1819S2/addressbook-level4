package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ClassForPrinting;
import seedu.address.model.Model;

/**
 * Checks the CAP and Workload limits set by the user for every semester against the modules the user plans to take.
 */
public class CheckLimitCommand extends Command {

    public static final String COMMAND_WORD = "cklimit";

    public static final String MESSAGE_SUCCESS = "All limits are checked.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ClassForPrinting s = model.checkLimit();
        model.setSelectedModuleTaken(s);
        model.setSelectedModuleTaken(s);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
