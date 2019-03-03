package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Statistics;

/**
 * Gets the Statistics of the  clinic.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";
    public static final String COMMAND_ALIAS = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays the statistics of the queried topic from the"
            + "given date range (Default: current month).\n"
            + "Parameters: TOPIC [MMYY] [MMYY]\n"
            + "Example: " + COMMAND_WORD + " expenditure 0119";

    private final YearMonth fromYearMonth;
    private final YearMonth toYearMonth;

    public StatisticsCommand(YearMonth from, YearMonth to) {
        requireNonNull(from);
        requireNonNull(to);
        this.fromYearMonth = from;
        this.toYearMonth = to;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Statistics stats = model.getStatistics(this.fromYearMonth, this.toYearMonth);
        return new CommandResult(
                String.format(stats.toString()));
    }
}
