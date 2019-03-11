package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;

public class ListReviewCommand extends Command{
    public static final String COMMAND_WORD = "listReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all reviews whose name equals"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Alice in Wonderland";

    public static final String MESSAGE_SUCCESS = "Listed all review satisfy the requirement";

    private final BookNameContainsExactKeywordsPredicate predicate;

    public ListReviewCommand(BookNameContainsExactKeywordsPredicate predicate) { this.predicate = predicate; }

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
                || (other instanceof ListReviewCommand // instanceof handles nulls
                && predicate.equals(((ListReviewCommand) other).predicate)); // state check
    }
}
