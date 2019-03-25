package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.travel.commons.core.Messages;
import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.place.YearContainsKeywordsPredicate;

/**
 * Finds and lists all places in travel book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchYearCommand extends Command {

    public static final String COMMAND_WORD = "searchyear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose year visited contain "
            + "the specified year or year range and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Parameters: KEYWORD-KEYWORD\n"
            + "Example: " + COMMAND_WORD + " 2018 2019"
            + "Example: " + COMMAND_WORD + " 2016-2018";

    private final YearContainsKeywordsPredicate predicate;

    public SearchYearCommand(YearContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPlaceList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PLACES_LISTED_OVERVIEW, model.getFilteredPlaceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchYearCommand // instanceof handles nulls
                && predicate.equals(((SearchYearCommand) other).predicate)); // state check
    }
}
