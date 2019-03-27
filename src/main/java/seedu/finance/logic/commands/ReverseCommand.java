package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;

/**
 * Reverses the records in the finance tracker.
 */
public class ReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";
    public static final String COMMAND_ALIAS = "rev";


    public static final String MESSAGE_SUCCESS = "Order of the list is reversed";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.reverseFilteredRecordList();

        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        model.commitFinanceTracker();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
