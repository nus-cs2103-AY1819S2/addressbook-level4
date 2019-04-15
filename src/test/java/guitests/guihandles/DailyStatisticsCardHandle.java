package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.statistics.DailyRevenue;

/**
 * Provides a handle to a dailyStatistics card in the statistics flow panel.
 */
public class DailyStatisticsCardHandle extends NodeHandle<Node> {
    private static final String DATE_FIELD_ID = "#date";
    private static final String TOTAL_REVENUE_FIELD_ID = "#totalDailyRevenue";

    private final Label dateLabel;
    private final Label totalDailyRevenueLabel;

    public DailyStatisticsCardHandle(Node cardNode) {
        super(cardNode);

        totalDailyRevenueLabel = getChildNode(DATE_FIELD_ID);
        dateLabel = getChildNode(TOTAL_REVENUE_FIELD_ID);
    }

    public String getDate() {
        return totalDailyRevenueLabel.getText();
    }

    public String getTotalRevenue() {
        return dateLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code dailyRevenue}.
     */
    public boolean equals(DailyRevenue dailyRevenue) {

        ArrayList<String> monthsInWords = new
                ArrayList<>(List.of("-Jan-", "-Feb-", "-Mar-", "-Apr-", "-May-", "-Jun-", "-Jul-", "-Aug-",
                "-Sep-", "-Oct-", "-Nov-", "-Dec-"));
        StringBuilder date = new StringBuilder();
        date.append(dailyRevenue.getDay()).append(".")
                .append(monthsInWords.get(Integer.parseInt(dailyRevenue.getMonth().toString()) - 1))
                .append(".").append(dailyRevenue.getYear());
        return getTotalRevenue().equals(dailyRevenue.getTotalRevenue())
                && getDate().equals(date);
    }
}
