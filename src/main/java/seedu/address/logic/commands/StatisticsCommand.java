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
            + "given date range.\n"
            + "Parameters: TOPIC MMYY [MMYY]\n"
            + "List of TOPICS: finances, consultations, all"
            + "Example: " + COMMAND_WORD + " all 0119";

    private final String topic;
    private final YearMonth fromYearMonth;
    private final YearMonth toYearMonth;

    public StatisticsCommand(String topic, YearMonth from, YearMonth to) {
        requireNonNull(topic);
        requireNonNull(from);
        requireNonNull(to);
        this.topic = topic;
        this.fromYearMonth = from;
        this.toYearMonth = to;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Statistics stats = model.getStatistics(this.topic, this.fromYearMonth, this.toYearMonth);
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying result for ")
                .append(this.topic)
                .append(" from ")
                .append(this.fromYearMonth.toString())
                .append(" to ")
                .append(this.toYearMonth.toString())
                .append(".\n")
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
        return this.topic.equals(sc.topic)
                && this.toYearMonth.equals(sc.fromYearMonth)
                && this.fromYearMonth.equals(sc.fromYearMonth);
    }
}
