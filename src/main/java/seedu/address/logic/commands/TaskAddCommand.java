package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_CLASH;
import static seedu.address.commons.core.Messages.MESSAGE_TIME_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;


/**
 * Adds a patient to the address book.
 */
public class TaskAddCommand extends Command {

    public static final String COMMAND_WORD = "taskadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_STARTDATE + "START DATE "
            + PREFIX_ENDDATE + "END DATE "
            + PREFIX_STARTTIME + "1100 "
            + PREFIX_ENDTIME + "1200\n"
            + "TITLE, START DATE, END DATE, START TIME and END TIME are mandatory fields and must be provided.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Teeth removal surgery "
            + PREFIX_STARTDATE + "10-11-2019 "
            + PREFIX_ENDDATE + "22-12-2019 "
            + PREFIX_STARTTIME + "1100 "
            + PREFIX_ENDTIME + "1200";


    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "An exact same task is already found in the records";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     * @param task the task to be added.
     */
    public TaskAddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (toAdd.hasDateClash()) {
            throw new CommandException(MESSAGE_DATE_CLASH);
        }
        if (toAdd.hasTimeClash()) {
            throw new CommandException(MESSAGE_TIME_CLASH);
        }
        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskAddCommand // instanceof handles nulls
                && toAdd.equals(((TaskAddCommand) other).toAdd));
    }
}
