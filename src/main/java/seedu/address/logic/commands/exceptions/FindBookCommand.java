package seedu.address.logic.commands.exceptions;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;

public class FindBookCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all books whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob caroline";

    private final BookNameContainsKeywordsPredicate predicate;

    public FindBookCommand(BookNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindBookCommand // instanceof handles nulls
                && predicate.equals(((FindBookCommand) other).predicate)); // state check
    }
}
