package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;

/**
 * Gets revenue from the Revenue list.
 */
public class RevenueCommand extends Command {

    public static final String COMMAND_WORD = "revenue";
    public static final String COMMAND_ALIAS = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gets the revenue from the specified year, month or day. "
            + "Parameters: [y/YEAR [m/MONTH] [d/DAY]]]\n"
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " y/2019 or "
            + COMMAND_WORD + " y/2019 m/12 or "
            + COMMAND_WORD + " y/2019 m/12 d/30";

    public static final String MESSAGE_SUCCESS = "Revenue for ";

    public static final String MESSAGE_REVENUE = "\n$ %.2f";

    private final Revenue revenue;

    private final ArrayList<String> monthsInWords = new
            ArrayList<>(List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
    /**
     * Creates an RevenueCommand to get revenue specified by the day, month or year.
     */
    public RevenueCommand(Revenue revenue) {
        requireAllNonNull(revenue);
        this.revenue = revenue;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) {
        requireNonNull(model);
        float revenue = 0;
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);

        List<Revenue> revenueList = model.getFilteredRevenueList();

        if (this.revenue.getDay() != null && this.revenue.getMonth() != null && this.revenue.getYear() != null) {
            //daily
            for (Revenue revenueItem : revenueList) {
                requireNonNull(revenueItem);

                if (this.revenue.isSameRevenue(revenueItem)) {
                    revenue += revenueItem.getTotalRevenue();
                }
            }
            sbFinalOutput.append(this.revenue.getDay()).append(" ")
                    .append(monthsInWords.get(Integer.parseInt(this.revenue.getMonth().toString()) - 1)).append(" ")
                    .append(this.revenue.getYear());

        } else if (this.revenue.getDay() == null && this.revenue.getMonth() != null
                && this.revenue.getYear() != null) {
            //monthly

            Month requestedMonth = this.revenue.getMonth();
            Year requestedYear = this.revenue.getYear();
            for (Revenue revenueItem : revenueList) {
                requireNonNull(revenueItem);
                Month month = revenueItem.getMonth();
                Year year = revenueItem.getYear();

                if (requestedMonth.equals(month) && requestedYear.equals(year)) {
                    revenue += revenueItem.getTotalRevenue();
                }
            }
            sbFinalOutput.append(monthsInWords.get(Integer.parseInt(requestedMonth.toString()) - 1)).append(" ")
                    .append(this.revenue.getYear());

        } else if (this.revenue.getDay() == null && this.revenue.getMonth() == null
                && this.revenue.getYear() != null) {
            //yearly

            for (Revenue revenueItem : revenueList) {
                requireNonNull(revenueItem);
                Year requestedYear = this.revenue.getYear();
                Year year = revenueItem.getYear();

                if (requestedYear.equals(year)) {
                    revenue += revenueItem.getTotalRevenue();
                }
            }
            sbFinalOutput.append("Year ").append(this.revenue.getYear());

        } else if (this.revenue.getDay() == null && this.revenue.getMonth() == null
                && this.revenue.getYear() == null) {
            //current date
            java.util.Date currentDate = new java.util.Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
            String dateParser = dateFormatter.format(currentDate);
            Day requestedDay = new Day(dateParser.substring(0, 2));
            Month requestedMonth = new Month(dateParser.substring(3, 5));
            Year requestedYear = new Year(dateParser.substring(6, 10));

            for (Revenue revenueItem : revenueList) {
                requireNonNull(revenueItem);
                Day day = revenueItem.getDay();
                Month month = revenueItem.getMonth();
                Year year = revenueItem.getYear();

                if (requestedDay.equals(day) && requestedMonth.equals(month) && requestedYear.equals(year)) {
                    revenue += revenueItem.getTotalRevenue();
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
                && revenue.equals(((RevenueCommand) other).revenue));
    }
}
