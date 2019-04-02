package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LISTNUMBER;
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
public class MovePersonCommand extends Command {

    public static final String COMMAND_WORD = "movePerson";
    public static final String COMMAND_ALIAS = "mp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": copies person with NRIC from source list to dest list in job. "
            + "Parameters: "
            + PREFIX_JOBNAME + "JobName "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_LISTNUMBER + "source "
            + PREFIX_LISTNUMBER + "destination "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBNAME + "Helper "
            + PREFIX_NRIC + "S1234567U "
            + PREFIX_LISTNUMBER + "0 "
            + PREFIX_LISTNUMBER + "1 "
            + "The alias \"mp\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_JOBNAME + "Helper "
            + PREFIX_NRIC + "S1234567U "
            + PREFIX_LISTNUMBER + "0 "
            + PREFIX_LISTNUMBER + "1 ";

    public static final String MESSAGE_SUCCESS_1 = "Moved person with nric: %1$s ";
    public static final String MESSAGE_SUCCESS_2 = "in job: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "The dest list already has a person with this NRIC";
    public static final String MESSAGE_MISSING_PERSON = "This Person with NRIC is not in the source list";
    public static final String MESSAGE_MISSING_JOB = "This job does not exist";
    public static final String MESSAGE_SOURCE_OUT_OF_RANGE = "Source is out of range (0-3)";
    public static final String MESSAGE_DEST_OUT_OF_RANGE = "Dest is out of range (0-3)";

    private final JobName toAdd;
    private final Nric toAddNric;
    private final Integer from;
    private final Integer to;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public MovePersonCommand(JobName name, Nric nric, Integer source, Integer dest) {
        requireNonNull(name);
        requireNonNull(nric);
        requireNonNull(source);
        requireNonNull(dest);
        toAdd = name;
        toAddNric = nric;
        from = source;
        to = dest;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Job tempJob = new Job(toAdd);
        if (!model.hasJob(tempJob)) {
            throw new CommandException(MESSAGE_MISSING_JOB);
        }

        Person tempPerson = new Person(toAddNric);

        if (from > 3 || from < 0) {
            throw new CommandException(MESSAGE_SOURCE_OUT_OF_RANGE);
        }
        if (to > 3 || to < 0) {
            throw new CommandException(MESSAGE_DEST_OUT_OF_RANGE);
        }

        int status = model.movePerson(toAdd, toAddNric, from, to);

        if (status == 0) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        }
        if (status == 1) {
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
                || (other instanceof MovePersonCommand // instanceof handles nulls
                && toAdd.equals(((MovePersonCommand) other).toAdd));
    }
}
