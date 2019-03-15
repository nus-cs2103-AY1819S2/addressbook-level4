package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.tag.Tag;

/**
 * Display the success rate of the past quiz mode. Keyword matching is case insensitive.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_STATISTICS_FORMAT = "Success rate: %.2f %%.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display the success rate from quiz mode. "
            + "Parameters: ";

    private final Set<Tag> tags;


    /**
     * Creates a StatsCommand to display the success rate
     */
    public StatsCommand() {
        tags = new HashSet<>();
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Flashcard> filteredPersonList = model.getFilteredFlashcardList();
        Statistics cumulativeStats = new Statistics();

        for (Flashcard flashcard : filteredPersonList) {
            cumulativeStats = cumulativeStats.merge(flashcard.getStatistics());
        }

        double percentageSuccessRate = cumulativeStats.getSuccessRate() * 100;

        return new CommandResult(String.format(MESSAGE_STATISTICS_FORMAT, percentageSuccessRate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatsCommand // instanceof handles nulls
                && tags.equals(((StatsCommand) other).tags));
    }
}
