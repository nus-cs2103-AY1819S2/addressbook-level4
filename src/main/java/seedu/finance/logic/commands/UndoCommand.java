package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;

/**
 * Reverts the {@code model}'s finance tracker to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoFinanceTracker()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (model.isSetFile()) {
            model.addUndoPreviousDataFile(model.getFinanceTrackerFilePath());
            model.changeFinanceTrackerFile(model.removePreviousDataFile());
        }
        model.undoFinanceTracker();
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }
}
