package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;

public class ListReviewCommand extends Command{
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all reviews whose name equals"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME\n"
            + "Example: " + COMMAND_WORD + " Alice in Wonderland";

    public static final String MESSAGE_SUCCESS = "Listed all review satisfy the requirement";

    private final BookNameContainsExactKeywordsPredicate predicate;

    public ListReviewCommand(BookNameContainsExactKeywordsPredicate predicate) { this.predicate = predicate; }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListReviewCommand // instanceof handles nulls
                && predicate.equals(((ListReviewCommand) other).predicate)); // state check
    }
}
