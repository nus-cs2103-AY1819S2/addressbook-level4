package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;


/**
 * Adds a person to the address book.
 */
public class DisplayJobCommand extends Command {

    public static final String COMMAND_WORD = "DisplayJob";
    public static final String COMMAND_ALIAS = "dj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays a list from a job. "
            + "Parameters: "
            + PREFIX_JOBNAME + "JobName "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBNAME + "Helper "
            + "The alias \"dj\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_JOBNAME + "Helper ";

    public static final String MESSAGE_SUCCESS = "Displaying job";
    public static final String MESSAGE_MISSING_JOB = "This job does not exist";

    private final JobName toAdd;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public DisplayJobCommand(JobName name) {
        requireNonNull(name);
        toAdd = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Job tempJob = new Job(toAdd);
        if (!model.hasJob(tempJob)) {
            throw new CommandException(MESSAGE_MISSING_JOB);
        }

        model.getJob(toAdd);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), toAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayJobCommand // instanceof handles nulls
                && toAdd.equals(((DisplayJobCommand) other).toAdd));
    }
}
