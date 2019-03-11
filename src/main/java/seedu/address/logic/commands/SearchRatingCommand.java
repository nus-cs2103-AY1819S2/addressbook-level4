package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.place.RatingContainsKeywordsPredicate;

/**
 * Finds and lists all places in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchRatingCommand extends Command {

    public static final String COMMAND_WORD = "searchr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose ratings contain "
            + "the specified ratings and displays them as a list with index numbers.\n"
            + "Parameters: RATING [MORE_RATINGS]...\n"
            + "Example: " + COMMAND_WORD + " 4 5";

    private final RatingContainsKeywordsPredicate predicate;

    public SearchRatingCommand(RatingContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPlaceList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPlaceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchRatingCommand // instanceof handles nulls
                && predicate.equals(((SearchRatingCommand) other).predicate)); // state check
    }
}
