package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.model.Model;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;

/**
 * Finds and lists all decks in the current list, which has identity field contains
 * any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindDeckCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all decks in the current list,"
            + " which has identity field contains any of the argument keywords.\n"
            + "The specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " John Doe";
    public static final String DEFAULT_STRING = "John Doe";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_STRING;

    private final DecksView decksView;
    private final DeckNameContainsKeywordsPredicate predicate;

    public FindDeckCommand(DecksView decksView, DeckNameContainsKeywordsPredicate deckPredicate) {
        this.decksView = decksView;
        this.predicate = deckPredicate;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        decksView.updateFilteredList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DECKS_LISTED_OVERVIEW, decksView.getFilteredList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDeckCommand // instanceof handles nulls
                && predicate.equals(((FindDeckCommand) other).predicate)); // state check
    }
}

