package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format statistics of existing conitions in the request book .
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_SUCCESS = "Statistics displayed in descending order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the conditions existent in "
            + "the request book and the number of occurences of each condition. Formatted from most common"
            + "to least common health condition";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //Statistics.getStatistics();
        return new CommandResult(Statistics.class.toString() + MESSAGE_SUCCESS);
    }
}
