package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.place.TagContainsKeywordsPredicate;

/**
 * Finds and lists all places in address book whose tags contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchTagsCommand extends Command {

    public static final String COMMAND_WORD = "searcht";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose tags contain "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " shoppingMall school";

    private final TagContainsKeywordsPredicate predicate;

    public SearchTagsCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof SearchTagsCommand // instanceof handles nulls
                && predicate.equals(((SearchTagsCommand) other).predicate)); // state check
    }
}
