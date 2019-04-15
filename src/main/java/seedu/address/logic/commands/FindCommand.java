package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUCCESS_RATE_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashcardPredicate;

/**
 * Finds and lists all flashcards which contain any of the argument keywords AND which are within
 * a specified success rate range. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all flashcards which contain any of "
            + "the specified keywords (case-insensitive) AND which are within a specified success rate range. "
            + "displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_FRONT_FACE + "FRONTFACE] "
            + "[" + PREFIX_BACK_FACE + "BACKFACE] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_SUCCESS_RATE_RANGE + "SUCCESS_RATE_RANGE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FRONT_FACE + "Hello Ciao "
            + PREFIX_BACK_FACE + "Hola "
            + PREFIX_TAG + "Chinese "
            + PREFIX_TAG + "Spanish "
            + PREFIX_SUCCESS_RATE_RANGE + "0 50\n";

    private static final String MESSAGE_IN_QUIZ = "Cannot find in quiz mode.";

    private final FlashcardPredicate predicate;

    public FindCommand(FlashcardPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }
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
