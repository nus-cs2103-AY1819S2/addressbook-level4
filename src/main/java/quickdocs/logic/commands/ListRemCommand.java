package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_FORMAT;

import java.time.LocalDate;

import quickdocs.logic.CommandHistory;
import quickdocs.model.reminder.ReminderWithinDatesPredicate;
import quickdocs.model.Model;

/**
 * Lists filtered reminders on the sidebar to the user.
 */
public class ListRemCommand extends Command {

    public static final String COMMAND_WORD = "listrem";
    public static final String COMMAND_ALIAS = "lr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists reminders on sidebar.\n"
            + "Parameters: "
            + PREFIX_FORMAT + "FORMAT "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FORMAT + "week "
            + PREFIX_DATE + "2019-10-23\n";

    public static final String MESSAGE_SUCCESS = "Listed all reminders from %1$s to %2$s on the sidebar\n";

    private final LocalDate start;
    private final LocalDate end;

    public ListRemCommand(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(start, end);
        model.updateFilteredReminderList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, start, end), false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListRemCommand // instanceof handles nulls
                && start.equals(((ListRemCommand) other).start)
                && end.equals(((ListRemCommand) other).end)); // state check
    }
}
