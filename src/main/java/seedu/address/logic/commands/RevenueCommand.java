package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;

/**
 * Gets revenue from the Daily revenue list.
 */
public class RevenueCommand extends Command {

    public static final String COMMAND_WORD = "revenue";
    public static final String COMMAND_ALIAS = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gets the revenue from the specified year, month or day."
            + "Parameters: [y/YEAR [m/MONTH] [d/DAY]]]\n"
            + "Example: " + COMMAND_WORD + " y/2019 or "
            + COMMAND_WORD + "y/2019 m/12 or "
            + COMMAND_WORD + "y/2019 m/12 d/30";

    public static final String MESSAGE_SUCCESS = "Revenue for ";

    public static final String MESSAGE_REVENUE = "\n$ %.2f";

    private final DailyRevenue dailyRevenue;

    private final ArrayList<String> monthsInWords = new
            ArrayList<>(List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
    /**
     * Creates an RevenueCommand to get revenue specified by the day, month or year.
     */
    public RevenueCommand(DailyRevenue dailyRevenue) {
        requireAllNonNull(dailyRevenue);
        this.dailyRevenue = dailyRevenue;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) {
        requireNonNull(model);
        float revenue = 0;
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);

        List<DailyRevenue> dailyRevenueList = model.getFilteredDailyRevenueList();

        if (dailyRevenue.getDay() != null && dailyRevenue.getMonth() != null && dailyRevenue.getYear() != null) {
            //daily
            for (DailyRevenue dailyRevenueItem : dailyRevenueList) {
                requireNonNull(dailyRevenueItem);

                if (dailyRevenue.isSameDailyRevenue(dailyRevenueItem)) {
                    revenue += dailyRevenueItem.getTotalDailyRevenue();
                }
            }
            sbFinalOutput.append(dailyRevenue.getDay()).append(" ")
                    .append(monthsInWords.get(Integer.parseInt(dailyRevenue.getMonth().toString()) - 1)).append(" ")
                    .append(dailyRevenue.getYear());

        } else if (dailyRevenue.getDay() == null && dailyRevenue.getMonth() != null
                && dailyRevenue.getYear() != null) {
            //monthly

            Month requestedMonth = dailyRevenue.getMonth();
            Year requestedYear = dailyRevenue.getYear();
            for (DailyRevenue dailyRevenueItem : dailyRevenueList) {
                requireNonNull(dailyRevenueItem);
                Month month = dailyRevenueItem.getMonth();
                Year year = dailyRevenueItem.getYear();

                if (requestedMonth.equals(month) && requestedYear.equals(year)) {
                    revenue += dailyRevenueItem.getTotalDailyRevenue();
                }
            }
            sbFinalOutput.append(monthsInWords.get(Integer.parseInt(requestedMonth.toString()) - 1)).append(" ")
                    .append(dailyRevenue.getYear());

        } else if (dailyRevenue.getDay() == null && dailyRevenue.getMonth() == null
                && dailyRevenue.getYear() != null) {
            //yearly

            for (DailyRevenue dailyRevenueItem : dailyRevenueList) {
                requireNonNull(dailyRevenueItem);
                Year requestedYear = dailyRevenue.getYear();
                Year year = dailyRevenueItem.getYear();

                if (requestedYear.equals(year)) {
                    revenue += dailyRevenueItem.getTotalDailyRevenue();
                }
            }
            sbFinalOutput.append("Year ").append(dailyRevenue.getYear());

        } else if (dailyRevenue.getDay() == null && dailyRevenue.getMonth() == null
                && dailyRevenue.getYear() == null) {
            //current date
            java.util.Date currentDate = new java.util.Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
            String dateParser = dateFormatter.format(currentDate);
            Day requestedDay = new Day(dateParser.substring(0, 2));
            Month requestedMonth = new Month(dateParser.substring(3, 5));
            Year requestedYear = new Year(dateParser.substring(6, 10));

            for (DailyRevenue dailyRevenueItem : dailyRevenueList) {
                requireNonNull(dailyRevenueItem);
                Day day = dailyRevenueItem.getDay();
                Month month = dailyRevenueItem.getMonth();
                Year year = dailyRevenueItem.getYear();

                if (requestedDay.equals(day) && requestedMonth.equals(month) && requestedYear.equals(year)) {
                    revenue += dailyRevenueItem.getTotalDailyRevenue();
                }
            }
            sbFinalOutput.append(requestedDay.toString()).append(" ")
                    .append(monthsInWords.get(Integer.parseInt(requestedMonth.toString()) - 1)).append(" ")
                    .append(requestedYear.toString());
        }
        sbFinalOutput.append(String.format(MESSAGE_REVENUE, revenue));
        return new CommandResult(sbFinalOutput.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RevenueCommand
                && dailyRevenue.equals(((RevenueCommand) other).dailyRevenue));
    }
}
