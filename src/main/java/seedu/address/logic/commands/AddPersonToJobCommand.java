package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;


/**
 * Adds a person to the address book.
 */
public class AddPersonToJobCommand extends Command {

    public static final String COMMAND_WORD = "addPersonToJob";
    public static final String COMMAND_ALIAS = "aj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds person to job using Nric. "
            + "Parameters: "
            + PREFIX_JOBNAME + "JobName "
            + PREFIX_NRIC + "NRIC "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBNAME + "Helper "
            + PREFIX_NRIC + "S1234567U "
            + "The alias \"aj\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_JOBNAME + "Helper "
            + PREFIX_NRIC + "S1234567U ";

    public static final String MESSAGE_SUCCESS_1 = "Added person with nric: %1$s ";
    public static final String MESSAGE_SUCCESS_2 = "to job: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Job already has a person with this NRIC";
    public static final String MESSAGE_MISSING_PERSON = "This NRIC is not in the addressbook";
    public static final String MESSAGE_MISSING_JOB = "This job does not exist";

    private final JobName toAdd;
    private final Nric toAddNric;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public AddPersonToJobCommand(JobName name, Nric nric) {
        requireNonNull(name);
        requireNonNull(nric);
        toAdd = name;
        toAddNric = nric;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Job tempJob = new Job(toAdd);
        if (!model.hasJob(tempJob)) {
            throw new CommandException(MESSAGE_MISSING_JOB);
        }

        Person tempPerson = new Person(toAddNric);
        if (!model.hasPerson(tempPerson)) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }

        boolean status = model.addPersonToJob(toAdd, toAddNric);

        if (!status) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.commitAddressBook();
        String command1 = String.format(MESSAGE_SUCCESS_1, toAddNric);
        String command2 = String.format(MESSAGE_SUCCESS_2, toAdd);
        return new CommandResult(command1 + command2);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPersonToJobCommand // instanceof handles nulls
                && toAdd.equals(((AddPersonToJobCommand) other).toAdd));
    }
}
