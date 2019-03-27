package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Finds and lists all requests in request book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindRequestCommand extends Command {

    public static final String COMMAND_WORD = "frequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " request: Finds all requests whose"
        + " names contain any of the specified keywords (case-sensitive) and displays them as a "
        + "list with index numbers. \n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " request alice bob charlie";

    private final Predicate<Request> predicate;

    public FindRequestCommand(Predicate<Request> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredRequestList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_REQUESTS_LISTED_OVERVIEW,
            model.getFilteredRequestList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindRequestCommand) // instanceof handles nulls
            && predicate.equals(((FindRequestCommand) other).predicate);
    }

}
