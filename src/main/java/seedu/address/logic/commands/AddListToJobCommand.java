package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;


/**
 * Adds a person to the address book.
 */
public class AddListToJobCommand extends Command {

    public static final String COMMAND_WORD = "addAll";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds all shown people to job . "
            + "Parameters: "
            + PREFIX_JOBNAME + "JobName "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBNAME + "Helper "
            + "The alias \"aa\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_JOBNAME + "Helper ";

    public static final String MESSAGE_SUCCESS = "All people added to job: %1$s ";
    public static final String MESSAGE_MISSING_JOB = "This job does not exist";

    private final JobName toAdd;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public AddListToJobCommand(JobName name) {
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

        model.addFilteredPersonsToJob(toAdd);

        model.commitAddressBook();
        String command = String.format(MESSAGE_SUCCESS, toAdd);
        return new CommandResult(command);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddListToJobCommand // instanceof handles nulls
                && toAdd.equals(((AddListToJobCommand) other).toAdd));
    }
}
