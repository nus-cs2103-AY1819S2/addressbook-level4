package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.restaurant.categories.CategoryContainsKeywordsPredicate;

/**
 * Filters and lists all restaurants in food diary whose category matches any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all restaurants whose category matches any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CUISINE\n"
            + "Example: " + COMMAND_WORD + " Fast Food";

    private final CategoryContainsKeywordsPredicate predicate;

    public FilterCommand(CategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRestaurantList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RESTAURANTS_LISTED_OVERVIEW, model.getFilteredRestaurantList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
