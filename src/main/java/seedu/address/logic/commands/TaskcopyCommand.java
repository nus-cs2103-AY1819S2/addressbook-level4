package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Copy a temporary person to the address book.
 */
public class TaskcopyCommand extends Command {

    public static final String COMMAND_WORD = "taskcopy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Have a temporary duplicate task in the addressbook. "
            + "Parameters: Index (Must be an integer) [Number of Copies]"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person copied: %1$s";

    private final Index index;

    private final int numOfCopies;

    /**
     * Creates an TaskcopyCommand to add the specified {@code Person}
     */
    public TaskcopyCommand(Index index, int numOfCopies) {
        requireNonNull(index);
        this.index = index;
        this.numOfCopies = numOfCopies;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Task taskToCopy = lastShownList.get(index.getZeroBased());
        Task copytask = taskToCopy;

        for (int i = 0; i < numOfCopies; i++) {
            requireNonNull(taskToCopy);
            copytask = taskToCopy.copy();
            model.addTask(copytask);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, copytask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskcopyCommand // instanceof handles nulls
                && index.equals(((TaskcopyCommand) other).index));
    }
}
