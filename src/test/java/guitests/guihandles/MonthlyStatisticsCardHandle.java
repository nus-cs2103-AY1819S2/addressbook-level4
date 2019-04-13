package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.statistics.MonthlyRevenue;

/**
 * Provides a handle to a monthlyStatistics card in the statistics flow panel.
 */
public class MonthlyStatisticsCardHandle extends NodeHandle<Node> {
    private static final String MONTH_FIELD_ID = "#month";
    private static final String YEAR_FIELD_ID = "#year";
    private static final String TOTAL_REVENUE_FIELD_ID = "#totalMonthlyRevenue";

    private final Label monthLabel;
    private final Label yearLabel;
    private final Label totalRevenueLabel;

    public MonthlyStatisticsCardHandle(Node cardNode) {
        super(cardNode);

        monthLabel = getChildNode(MONTH_FIELD_ID);
        yearLabel = getChildNode(YEAR_FIELD_ID);
        totalRevenueLabel = getChildNode(TOTAL_REVENUE_FIELD_ID);
    }

    public String getTotalRevenue() {
        return totalRevenueLabel.getText();
    }

    public String getMonth() {
        return monthLabel.getText();
    }

    public String getYear() {
        return yearLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code monthlyRevenue}.
     */
    public boolean equals(MonthlyRevenue monthlyRevenue) {

        ArrayList<String> monthsInWords = new
                ArrayList<>(List.of("JAN ", "FEB ", "MAR ", "APR ", "MAY ", "JUN ", "JUL ", "AUG ", "SEP ", "OCT ",
                "NOV ", "DEC "));
        StringBuilder month = new StringBuilder();
        month.append((monthsInWords.get(Integer.parseInt(monthlyRevenue.getMonth().toString()) - 1)));
        return getTotalRevenue().equals(monthlyRevenue.getTotalRevenue())
                && getMonth().equals(month) && getYear().equals(monthlyRevenue.getYear());
    }
}
