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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays the statistics from the given date range.\n"
            + "Parameters: MMYY [MMYY]\n"
            + "Example: " + COMMAND_WORD + " 0119";

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
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying result from ")
                .append(this.fromYearMonth.toString())
                .append(" to ")
                .append(this.toYearMonth.toString())
                .append(".\n\n")
                .append(stats.toString());
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StatisticsCommand)) {
            return false;
        }
        StatisticsCommand sc = (StatisticsCommand) other;
        return this.toYearMonth.equals(sc.toYearMonth) && this.fromYearMonth.equals(sc.fromYearMonth);
    }
}
