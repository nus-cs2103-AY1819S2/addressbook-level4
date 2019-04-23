package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Selects a request identified using it's displayed index from the request book.
 */
public class SelectRequestCommand extends Command {

    public static final String COMMAND_WORD = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the request identified by the index number used in the displayed request "
        + "list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_REQUEST_SUCCESS = "Selected Request: %1$s";

    private final Index targetIndex;

    public SelectRequestCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Request> filteredRequestList = model.getFilteredRequestList();

        if (targetIndex.getZeroBased() >= filteredRequestList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        model.setSelectedRequest(filteredRequestList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_REQUEST_SUCCESS,
            targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof SelectRequestCommand
            && targetIndex.equals(((SelectRequestCommand) other).targetIndex));
    }
}
