package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Deletes a reminder identified using it's displayed index from the reminder sidebar.
 */
public class DeleteRemCommand extends Command {

    public static final String COMMAND_WORD = "deleterem";
    public static final String COMMAND_ALIAS = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the reminder sidebar.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";
    public static final String MESSAGE_INVALID_REMINDER_INDEX = "The reminder index provided is invalid";

    private final Index targetIndex;
    private final LocalDate date;

    public DeleteRemCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        date = LocalDate.now();
    }

    public DeleteRemCommand(Index targetIndex, LocalDate date) {
        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reminder> lastShownList = model.getFilteredReminderList(date);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_REMINDER_INDEX);
        }

        Reminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRemCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRemCommand) other).targetIndex)
                && date.equals(((DeleteRemCommand) other).date)); // state check
    }
}
