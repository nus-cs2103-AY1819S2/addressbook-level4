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
public class MovePeopleCommand extends Command {

    public static final String COMMAND_WORD = "movePeople";
    public static final String COMMAND_ALIAS = "mp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": copies person to dest list from source list. "
            + "Displayed job is used unless optional JobName is provided. \n"
            + "Parameters: "
            + "DESTINATION_LIST_NAME "
            + "SOURCE_LIST_NAME "
            + "APPLICANT_INDEXES "
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

    public static final String MESSAGE_SUCCESS = "%1$s people added to job after removing duplicate people";
    public static final String MESSAGE_NO_DISPLAYED_JOB = "No job is displayed. "
            + "Please enter a jobName with jn/ prefixed\n"
            + "Source list can only be provided if there is a job displayed";
    public static final String MESSAGE_DISPLAYING_JOB_ERROR = "Displaying Job. Cannot have jn/ input\n"
            + "and SOURCE cannot be empty";
    public static final String MESSAGE_NO_DESTINATION = "Please provide a destination list\n";
    public static final String MESSAGE_NO_SOURCE = "Please provide a source list\n";
    public static final String MESSAGE_NO_INDEX = "Please provide some indexes to move\n";
    public static final String MESSAGE_BAD_INDEX = "One of the indexes is bad\n";
    public static final String MESSAGE_JOB_NOT_FOUND = "Given job does not exist in database\n";

    private final JobListName to;
    private final JobListName from;
    private final ArrayList<Index> indexes;
    private final JobName toAdd;
    private Integer numberAdded;

    /**
     * Creates an AddCommand to add the specified {@code job}
     */
    public MovePeopleCommand(JobListName to, JobListName from, ArrayList<Index> indexes, JobName jobName) {
        requireNonNull(to);
        requireNonNull(indexes);
        this.to = to;
        this.from = from;
        this.indexes = indexes;
        this.toAdd = jobName;
        this.numberAdded = indexes.size();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Job tempJob;

        if (model.getIsAllJobScreen()) {
            if (toAdd == null || !from.equals(JobListName.STUB)) {
                throw new CommandException(MESSAGE_NO_DISPLAYED_JOB);
            }
            try {
                model.getJob(toAdd);
            } catch (Exception e) {
                throw new CommandException(MESSAGE_JOB_NOT_FOUND);
            }
        } else {
            if (toAdd != null || from.equals(JobListName.STUB)) {
                throw new CommandException(MESSAGE_DISPLAYING_JOB_ERROR);
            }
        }

        tempJob = model.getActiveJob();

        List<Person> fromList = model.getJobsList(from);

        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i).getZeroBased() >= fromList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i < indexes.size(); i++) {
            Person toAdd = fromList.get(indexes.get(i).getZeroBased());
            try {
                model.addPersonToJob(tempJob, toAdd, to);
            } catch (Exception e) {
                this.numberAdded--;
            }
        }

        model.commitAddressBook();
        String command = String.format(MESSAGE_SUCCESS, numberAdded);
        return new CommandResult(command);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MovePeopleCommand // instanceof handles nulls
                && to.equals(((MovePeopleCommand) other).to)
                && from.equals(((MovePeopleCommand) other).from)
                && indexes.equals(((MovePeopleCommand) other).indexes)
                && (toAdd == null || toAdd.equals(((MovePeopleCommand) other).toAdd)));
    }
}
