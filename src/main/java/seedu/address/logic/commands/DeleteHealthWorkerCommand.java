package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Deletes a HealthWorker from the addressbook based on the index specified.
 */
public class DeleteHealthWorkerCommand extends DeleteCommand implements HealthWorkerCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the health worker identified by the index number used in the displayed health worker list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n";

    public static final String MESSAGE_DELETE_HEALTHWORKER_SUCCESS = "Deleted Health Worker: %1$s";

    public DeleteHealthWorkerCommand(Index index) {
        super(index);
    }

    @Override
    public void delete(Model model, Object toDelete) {
        model.deleteHealthWorker((HealthWorker) toDelete);
        model.commitAddressBook();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<HealthWorker> lastShownList = model.getFilteredHealthWorkerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        HealthWorker toDelete = lastShownList.get(index.getZeroBased());
        delete(model, toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_HEALTHWORKER_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteHealthWorkerCommand)) {
            return false;
        }

        return this.index.equals(((DeleteHealthWorkerCommand) other).index);
    }
}
