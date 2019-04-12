package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBNAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Person;


/**
 * Adds a person to the address book.
 */
public class MovePersonCommand extends Command {

    public static final String COMMAND_WORD = "movePeople";
    public static final String COMMAND_ALIAS = "mp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": copies person to dest list from source list. "
            + "Displayed job is used unless optional JobName is provided. \n"
            + "Parameters: "
            + "DESTINATION_LIST_NAME "
            + "SOURCE_LIST_NAME "
            + "applicant indexes"
            + PREFIX_JOBNAME + "JobName (OPTIONAL)\n"
            + "Example: " + COMMAND_WORD + " "
            + "applicant "
            + "kiv "
            + "1, 2, 3 "
            + PREFIX_JOBNAME + "King-Of-The-World \n"
            + "The alias \"mp\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " "
            + "applicant "
            + "kiv "
            + "1, 2, 3 "
            + PREFIX_JOBNAME + "High-On-Drugs ";

    public static final String MESSAGE_SUCCESS = "All selected people added to job: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "The dest list already has a person with this NRIC";
    public static final String MESSAGE_NO_DISPLAYED_JOB = "No job is displayed. "
            + "Please enter a jobName with jn/ prefixed";
    public static final String MESSAGE_NO_DESTINATION = "Please provide a destination list";
    public static final String MESSAGE_NO_SOURCE = "Please provide a source list";
    public static final String MESSAGE_BAD_INDEX = "One of the indexes is bad";
    public static final String MESSAGE_JOB_NOT_FOUND = "Given job does not exist in database";

    private final JobListName to;
    private final JobListName from;
    private final ArrayList<Index> indexes;
    private final JobName toAdd;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public MovePersonCommand(JobListName to, JobListName from, ArrayList<Index> indexes, JobName jobName) {
        requireNonNull(to);
        requireNonNull(from);
        requireNonNull(indexes);
        this.to = to;
        this.from = from;
        this.indexes = indexes;
        this.toAdd = jobName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Job tempJob;

        if (toAdd == null) {
            if (model.getIsAllJobScreen()) {
                throw new CommandException(MESSAGE_NO_DISPLAYED_JOB);
            }
            tempJob = model.getActiveJob();
        } else {
            tempJob = new Job(toAdd);
            try {
                model.getJob(toAdd);
            } catch (Exception e) {
                throw new CommandException(MESSAGE_JOB_NOT_FOUND);
            }
        }

        List<Person> fromList = model.getJobsList(from);

        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i).getZeroBased() >= fromList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person toAdd = fromList.get(indexes.get(i).getZeroBased());

            model.addPersonToJob(tempJob, toAdd, to);
        }


        model.commitAddressBook();
        String command = String.format(MESSAGE_SUCCESS, tempJob);
        return new CommandResult(command);
    }

}
