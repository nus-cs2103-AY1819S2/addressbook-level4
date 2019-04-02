package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;


/**
 * Adds a person to the address book.
 */
public class DeleteJobCommand extends Command {

    public static final String COMMAND_WORD = "deleteJob";
    public static final String COMMAND_ALIAS = "rmj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new job. "
            + "Parameters: "
            + PREFIX_JOBNAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBNAME + "Search Engineer "
            + "The alias \"rmj\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_JOBNAME + "Search Engineer ";

    public static final String MESSAGE_SUCCESS = "Removed Job: %1$s";
    public static final String MESSAGE_MISSING_JOB = "This Job doesn't exist in the list";

    private final Job toRm;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public DeleteJobCommand(Job job) {
        requireNonNull(job);
        toRm = job;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasJob(toRm)) {
            throw new CommandException(MESSAGE_MISSING_JOB);
        }

        model.deleteJob(toRm);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRm));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJobCommand // instanceof handles nulls
                && toRm.equals(((DeleteJobCommand) other).toRm));
    }
}
