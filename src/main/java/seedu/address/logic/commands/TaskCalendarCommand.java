package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;

/**
 * Displays a Calendar to user, highlighting dates with tasks with the highest priority
 * Accepts a single date as input
 */
public class TaskCalendarCommand extends Command {

    public static final String COMMAND_WORD = "taskcal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Displays a Calendar that highlights days with tasks\n"
            + "Given date should be in dd-mm-yyyy format\n"
            + "Example: " + COMMAND_WORD + " "
            + "13-05-2019\n"
            + "If no date is provided, the current date will be used\n";

    public static final String MESSAGE_DISPLAY_CALENDAR_SUCCESS = "Task Calendar displayed for Date: %1$s";

    public final DateCustom dateInput;
    private ContainsKeywordsPredicate predicate;


    public TaskCalendarCommand(DateCustom dateInput, ContainsKeywordsPredicate predicate) {
        requireNonNull(dateInput);
        this.predicate = predicate;
        this.dateInput = dateInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(MESSAGE_DISPLAY_CALENDAR_SUCCESS, dateInput.toString()), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskCalendarCommand // instanceof handles nulls
                && dateInput.equals(((TaskCalendarCommand) other).dateInput)); // state check
    }

}
