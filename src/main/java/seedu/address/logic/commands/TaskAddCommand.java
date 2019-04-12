package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_CLASH;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static seedu.address.commons.core.Messages.MESSAGE_TIME_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDPATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Task;


/**
 * Adds a task to the address book.
 */
public class TaskAddCommand extends Command {

    public static final String COMMAND_WORD = "taskadd";
    public static final String COMMAND_WORD2 = "tadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_STARTDATE + "START DATE "
            + PREFIX_ENDDATE + "END DATE "
            + PREFIX_STARTTIME + "START TIME "
            + PREFIX_ENDTIME + "END TIME "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_LINKEDPATIENT + "PATIENT INDEX\n"
            + "TITLE, START DATE, START TIME and END TIME are mandatory fields and must be provided.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Teeth removal surgery "
            + PREFIX_STARTDATE + "10-11-2019 "
            + PREFIX_ENDDATE + "22-12-2019 "
            + PREFIX_STARTTIME + "1100 "
            + PREFIX_ENDTIME + "1200 "
            + PREFIX_PRIORITY + "high "
            + PREFIX_LINKEDPATIENT + "2\n";


    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;
    private final Index targetIndex;

    /**
     * Creates an TaskAddCommand to add the specified {@code Task}
     * @param task the task to be added.
     */
    public TaskAddCommand(Task task, Index index) {
        requireNonNull(task);
        toAdd = task;
        targetIndex = index;
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
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() != 0) {
                int actualIndex = targetIndex.getZeroBased() - 1;
                List<Person> lastShownList = model.getFilteredPersonList();
                if (actualIndex >= lastShownList.size()) {
                    throw new CommandException(LinkedPatient.MESSAGE_CONSTRAINTS);
                }
                Person targetPerson = lastShownList.get(actualIndex);
                Patient targetPatient = (Patient) targetPerson;
                toAdd.setLinkedPatient(targetPatient.getName(), ((Patient) targetPerson).getNric());
            } else {
                toAdd.setNullLinkedPatient();
            }
        }
        if (model.hasTask(toAdd)) {
            model.updateFilteredTaskList(task -> (task.isSameTask(toAdd)));
            throw new CommandException(MESSAGE_DUPLICATE_TASK + "\nDuplicate task shown.");
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
