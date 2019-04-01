package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Date;
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
            + ": Gets the revenue from the specified day, month or year."
            + "Parameters: [d/DAY] [m/MONTH] [y/YEAR]\n"
            + "Combination of [d/DAY] and [y/YEAR] is not allowed.\n"
            + "Example: " + COMMAND_WORD + " d/30 m/12 y/2019 or "
            + COMMAND_WORD + " m/12 y/2019 or "
            + COMMAND_WORD + " y/2019";

    public static final String MESSAGE_SUCCESS = "Revenue for ";

    public static final String MESSAGE_REVENUE = "\n$ %.2f";

    private final DailyRevenue dailyRevenue;

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
            for (DailyRevenue dailyRevenueItem : dailyRevenueList) {
                requireNonNull(dailyRevenueItem);

                if (dailyRevenue.isSameDailyRevenue(dailyRevenueItem)) {
                    revenue += dailyRevenueItem.getTotalDailyRevenue();
                }
            }
            sbFinalOutput.append(dailyRevenue.getDay()).append(".")
                    .append(dailyRevenue.getMonth()).append(".")
                    .append(dailyRevenue.getYear());

        } else if (dailyRevenue.getDay() == null && dailyRevenue.getMonth() != null
                && dailyRevenue.getYear() != null) {

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
            sbFinalOutput.append(getMonth(requestedMonth.toString())).append(dailyRevenue.getYear());

        } else if (dailyRevenue.getDay() == null && dailyRevenue.getMonth() == null
                && dailyRevenue.getYear() != null) {

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
        }
        sbFinalOutput.append(String.format(MESSAGE_REVENUE, revenue));
        return new CommandResult(sbFinalOutput.toString());
    }

    private String getMonth(String month) {
        switch(Integer.parseInt(month)) {

            case 1:
                return("Jan ");
            case 2:
                return("Feb ");
            case 3:
                return("Mar ");
            case 4:
                return("Apr ");
            case 5:
                return("May ");
            case 6:
                return("Jun ");
            case 7:
                return("Jul ");
            case 8:
                return("Aug ");
            case 9:
                return("Sep ");
            case 10:
                return("Oct ");
            case 11:
                return("Nov ");
            case 12:
                return("Dec ");
            default:
                return(month);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RevenueCommand
                && dailyRevenue.equals(((RevenueCommand) other).dailyRevenue));
    }
}
