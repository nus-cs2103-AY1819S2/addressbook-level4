package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;

/**
 * Lists all records in the finance tracker to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String COMMAND_ALIAS2 = "ls";

    public static final String MESSAGE_SUCCESS = "Listed all records";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
