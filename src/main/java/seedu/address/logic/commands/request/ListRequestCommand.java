package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;

/**
 * Lists all requests in the request book to the user.
 */
public class ListRequestCommand extends ListCommand implements RequestCommand {

    public static final String MESSAGE_SUCCESS = "Listed all requests";

    public static final String COMMAND_WORD = "lr";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ListRequestCommand;
    }
}
