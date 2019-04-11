package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;

/**
 * Reverts the {@code model}'s finance tracker to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS = "r";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoFinanceTracker()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.redoFinanceTracker();
        if (model.isSetFile()) {
            model.addPreviousDataFile(model.getFinanceTrackerFilePath());
            model.changeFinanceTrackerFile(model.removeUndoPreviousDataFile());
        }
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }
}
