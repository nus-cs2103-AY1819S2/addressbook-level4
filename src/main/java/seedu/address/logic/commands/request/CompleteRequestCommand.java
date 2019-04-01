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
import seedu.address.model.request.RequestStatus;

/**
 * Marks a request as COMPLETED.
 */
public class CompleteRequestCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Marks an request as COMPLETED \n"
        + "Parameters: INDEX (must be a positive integer) \n"
        + "Example: " + COMMAND_WORD + " 1 ";


    public static final String MESSAGE_COMPLETED_REQUEST_SUCCESS = "Request %1$s have been completed.";
    public static final String MESSAGE_ONGOING_REQUEST = "Only ONGOING status can be marked as completed.";

    private final Index targetIndex;

    public CompleteRequestCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Request> lastShownList = model.getFilteredRequestList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        Request requestToBeCompleted = lastShownList.get(targetIndex.getZeroBased());

        if (!requestToBeCompleted.getRequestStatus().equals(new RequestStatus("ONGOING"))) {
            throw new CommandException(MESSAGE_ONGOING_REQUEST);
        }

        requestToBeCompleted.complete();

        model.updateRequest(requestToBeCompleted, requestToBeCompleted);
        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        model.commitRequestBook();

        return new CommandResult(String.format(MESSAGE_COMPLETED_REQUEST_SUCCESS, requestToBeCompleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CompleteRequestCommand // instanceof handles nulls
            && targetIndex.equals(((CompleteRequestCommand) other).targetIndex)); // state check
    }
}
