package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;

/**
 * Clears the finance tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String COMMAND_ALIAS2 = "clr";
    public static final String MESSAGE_SUCCESS = "Finance tracker has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.setFinanceTracker(new FinanceTracker());
        model.commitFinanceTracker();
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }
}
