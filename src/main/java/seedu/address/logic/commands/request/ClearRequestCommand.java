package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;

/**
 * Clears the address book.
 */
public class ClearRequestCommand extends Command {

    public static final String COMMAND_WORD = "clr";
    public static final String MESSAGE_SUCCESS = "Request book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        for (Request request : model.getRequestBook().getRequestList()) {
            if (request.isOngoingStatus()) {
                throw new CommandException(Messages.MESSAGE_REQUEST_ONGOING_CANNOT_CLEAR);
            }
        }
        model.resetData(new RequestBook());
        model.commitRequestBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
