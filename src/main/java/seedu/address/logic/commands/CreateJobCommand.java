package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;
import static seedu.address.model.job.JobListName.EMPTY;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.JobsApplyContainsKeywordsPredicate;


/**
 * Adds a person to the address book.
 */
public class CreateJobCommand extends Command {

    public static final String COMMAND_WORD = "createJob";
    public static final String COMMAND_ALIAS = "cj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new job. "
        + "Parameters: "
        + PREFIX_JOBNAME + "NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_JOBNAME + "Search Engineer "
        + "The alias \"cj\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS + " "
        + PREFIX_JOBNAME + "Search Engineer ";

    public static final String MESSAGE_SUCCESS = "New job created. All applicants added";
    public static final String MESSAGE_DUPLICATE_JOB = "This Job already exists in the list";

    private final Job toAdd;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public CreateJobCommand(Job job) {
        requireNonNull(job);
        toAdd = job;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasJob(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

        model.addJob(toAdd);
        ArrayList<String> jobNameCollection = new ArrayList<>();
        jobNameCollection.add(toAdd.getName().toString());
        Predicate<Person> predicator = new JobsApplyContainsKeywordsPredicate(jobNameCollection);
        model.updateFilteredPersonList(predicator);
        model.addFilteredPersonsToJob(toAdd.getName(), JobListName.STUB, JobListName.APPLICANT);
        model.commitAddressBook();
        model.updateFilteredPersonLists(EMPTY);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CreateJobCommand // instanceof handles nulls
            && toAdd.equals(((CreateJobCommand) other).toAdd));
    }
}
