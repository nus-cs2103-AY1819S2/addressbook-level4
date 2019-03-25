package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;

/**
 * Finds and lists all flashcards which contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all flashcards which contain any of "
            + "the specified keywords (case-insensitive) based on prefix and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_FRONT_FACE + "FRONTFACE] "
            + "[" + PREFIX_BACK_FACE + "BACKFACE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FRONT_FACE + "Hello Ciao "
            + PREFIX_BACK_FACE + "Hola "
            + PREFIX_TAG + "Chinese "
            + PREFIX_TAG + "Spanish \n";

    private final FlashcardContainsKeywordsPredicate predicate;

    public FindCommand(FlashcardContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW, model.getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
