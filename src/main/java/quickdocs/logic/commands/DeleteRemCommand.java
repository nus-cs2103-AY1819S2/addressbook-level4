package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.reminder.Reminder;

/**
 * Deletes a {@code Reminder} identified using it's displayed {@code Index} from the reminder sidebar.
 */
public class DeleteRemCommand extends Command {

    public static final String COMMAND_WORD = "deleterem";
    public static final String COMMAND_ALIAS = "dr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number displayed in the reminder sidebar.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted reminder: %1$s";
    public static final String MESSAGE_INVALID_REMINDER_INDEX = "The reminder index provided is invalid.";

    private final Index targetIndex;

    /**
     * Creates a {@code DeleteRemCommand} to delete the specified {@code Reminder}.
     */
    public DeleteRemCommand(Index targetIndex) {
        assert targetIndex.getOneBased() > 0;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList();

        // check if given targetIndex is valid
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_REMINDER_INDEX);
        }
        assert !lastShownList.isEmpty();

        // retrieve selected reminder and delete it
        Reminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRemCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRemCommand) other).targetIndex)); // state check
    }
}
