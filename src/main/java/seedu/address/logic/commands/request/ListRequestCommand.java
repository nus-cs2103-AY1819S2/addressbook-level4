package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all requests in the request book to the user.
 */
public class ListRequestCommand extends Command {

    public static final String COMMAND_WORD = "rl";

    public static final String MESSAGE_SUCCESS = "Listed all requests";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
