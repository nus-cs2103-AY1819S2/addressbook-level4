package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.address.model.flashcard.Statistics;

/**
 * Display the success rate of the past quiz mode. Keyword matching is case insensitive.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_STATISTICS_FORMAT = "Success rate: %.2f %%.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display the success rate of all flashcards which "
            + "contains any of the specified keywords (case-insensitive) based on prefix. If no keywords is specified, "
            + "this command will display the success rate of flashcards in the current list.\n"
            + "Parameters: "
            + "[" + PREFIX_FRONT_FACE + "FRONTFACE] "
            + "[" + PREFIX_BACK_FACE + "BACKFACE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FRONT_FACE + "Hello Ciao "
            + PREFIX_BACK_FACE + "Hola "
            + PREFIX_TAG + "Chinese "
            + PREFIX_TAG + "Spanish \n";
    private static final String MESSAGE_IN_QUIZ = "Cannot stats in quiz mode.";

    private final Optional<FlashcardContainsKeywordsPredicate> optPredicate;


    /**
     * Creates a StatsCommand to display the success rate of current filtered flashcards list.
     */
    public StatsCommand() {
        optPredicate = Optional.empty();
    }

    /**
     * Creates a StatsCommand with a predicate. The predicate will be used to filter the entire flashcards list.
     */
    public StatsCommand(FlashcardContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.optPredicate = Optional.of(predicate);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }

        optPredicate.ifPresent(model::updateFilteredFlashcardList);
        List<Flashcard> filteredPersonList = model.getFilteredFlashcardList();

        Statistics cumulativeStats = new Statistics();

        for (Flashcard flashcard : filteredPersonList) {
            cumulativeStats = cumulativeStats.merge(flashcard.getStatistics());
        }

        double successRate = cumulativeStats.getSuccessRate();
        double percentageSuccessRate = successRate * 100;

        return new CommandResult(String.format(MESSAGE_STATISTICS_FORMAT, percentageSuccessRate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatsCommand // instanceof handles nulls
                && optPredicate.equals(((StatsCommand) other).optPredicate));
    }
}
