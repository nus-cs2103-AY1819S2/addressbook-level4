package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class RemoveFromListCommand extends Command {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the person identified by the index number used in the specified list.\n"
            + "Parameters: LIST_NAME "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " kiv" + " 1\n"
            + "The alias \"d\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + " kiv" + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Removed People";
    public static final String MESSAGE_NO_LIST_NAME = "Please specify which list to remove from";
    public static final String MESSAGE_NO_DISPLAYED_JOB = "No job is displayed. "
            + "remove command can only be used when there is an active job.";
    public static final String MESSAGE_BAD_INDEX = "One of the indexes is bad";

    private final JobListName targetList;
    private final ArrayList<Index> indexes;

    public RemoveFromListCommand(JobListName targetList, ArrayList<Index> indexes) {
        this.targetList = targetList;
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ArrayList<Person> peopleToRemove = new ArrayList<>();

        if (model.getIsAllJobScreen()) {
            throw new CommandException(MESSAGE_NO_DISPLAYED_JOB);
        }

        List<Person> list = model.getJobsList(targetList);

        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i).getZeroBased() >= list.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            peopleToRemove.add(list.get(indexes.get(i).getZeroBased()));
        }

        for (int i = 0; i < indexes.size(); i++) {
            model.deletePersonFromJobList(peopleToRemove.get(i), model.getActiveJob().getName(), targetList);
        }


        model.commitAddressBook();
        return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
    }

}
