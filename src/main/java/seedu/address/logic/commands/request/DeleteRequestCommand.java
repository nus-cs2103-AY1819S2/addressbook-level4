package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Deletes a request identified using it's displayed index from the request book.
 */
public class DeleteRequestCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the request identified "
        + "by the index number used in the displayed request list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REQUEST_SUCCESS = "Deleted Request: %1$s";

    public DeleteRequestCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public void delete(Model model, Object toDelete) {
        model.deleteRequest((Request) toDelete);
        model.commitRequestBook();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Request> lastShownList = model.getFilteredRequestList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        Request requestToDelete = lastShownList.get(index.getZeroBased());
        delete(model, requestToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REQUEST_SUCCESS, requestToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof DeleteRequestCommand
            && index.equals(((DeleteRequestCommand) other).index));
    }
}
