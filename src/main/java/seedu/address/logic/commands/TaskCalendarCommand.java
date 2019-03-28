package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.task.Task;

public class TaskCalendarCommand extends Command {

    public static final String COMMAND_WORD = "taskcal";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + "13-05-2019\n"
            + "If no date is provided, the current date will be used\n";

    public static final String MESSAGE_EXPAND_PERSON_SUCCESS = "Task Calendar displayed";

    public final DateCustom dateInput;

    public TaskCalendarCommand(DateCustom dateInput) {
        requireNonNull(dateInput);
        this.dateInput = dateInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskCalendarCommand // instanceof handles nulls
                && dateInput.equals(((TaskCalendarCommand) other).dateInput)); // state check
    }

}
