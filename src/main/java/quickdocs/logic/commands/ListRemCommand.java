package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_FORMAT;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_INDEX;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.reminder.Reminder;
import quickdocs.model.reminder.ReminderWithinDatesPredicate;

/**
 * Lists filtered {@code Reminder}(s) on the reminder sidebar of the UI.
 */
public class ListRemCommand extends Command {

    public static final String COMMAND_WORD = "listrem";
    public static final String COMMAND_ALIAS = "lr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists reminders on the reminder sidebar.\n"
            + "Parameters: "
            + PREFIX_FORMAT + "FORMAT "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "week "
            + PREFIX_DATE + "2019-10-23\n"
            + "OR\n"
            + "Parameters: " + PREFIX_INDEX + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Listed all reminders from %1$s to %2$s on the sidebar.\n";
    public static final String MESSAGE_SINGLE_REMINDER_SUCCESS = "Displaying reminder #%1$s:\n"
            + "============================================\n"
            + "%2$s";
    public static final String MESSAGE_INVALID_REMINDER_INDEX = "The reminder index provided is invalid.";

    private final LocalDate start;
    private final LocalDate end;
    private final Index targetIndex;

    /**
     * Creates a {@code ListRemCommand} to list {@code Reminder}(s) on the reminder sidebar
     * in the given search range of dates.
     */
    public ListRemCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        targetIndex = null;
    }

    /**
     * Creates a {@code ListRemCommand} to display information of the selected {@code Reminder} based on
     * the given {@code Index}.
     */
    public ListRemCommand(Index targetIndex) {
        this.start = null;
        this.end = null;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // List single reminder
        if (Optional.ofNullable(targetIndex).isPresent()) {
            assert start == null;
            assert end == null;

            List<Reminder> lastShownList = model.getFilteredReminderList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_REMINDER_INDEX);
            }
            assert !lastShownList.isEmpty();

            Reminder reminderToList = lastShownList.get(targetIndex.getZeroBased());
            return new CommandResult(String.format(MESSAGE_SINGLE_REMINDER_SUCCESS,
                    targetIndex.getOneBased(), reminderToList));
        }

        // List reminder(s) on sidebar
        assert start != null;
        assert end != null;

        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(start, end);
        model.updateFilteredReminderList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, start, end), false, false);
    }

    @Override
    public boolean equals(Object other) {
        // Objects.equals() to handle null fields
        return other == this // short circuit if same object
                || (other instanceof ListRemCommand // instanceof handles nulls
                && Objects.equals(start, ((ListRemCommand) other).start)
                && Objects.equals(end, ((ListRemCommand) other).end)
                && Objects.equals(targetIndex, ((ListRemCommand) other).targetIndex));
    }
}
