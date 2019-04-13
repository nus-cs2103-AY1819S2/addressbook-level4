package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.statistics.YearlyRevenue;

/**
 * Provides a handle to a yearlyStatistics card in the statistics flow panel.
 */
public class YearlyStatisticsCardHandle extends NodeHandle<Node> {
    private static final String DATE_FIELD_ID = "#year";
    private static final String TOTAL_REVENUE_FIELD_ID = "#totalYearlyRevenue";

    private final Label yearLabel;
    private final Label totalYearlyRevenueLabel;

    public YearlyStatisticsCardHandle(Node cardNode) {
        super(cardNode);

        totalYearlyRevenueLabel = getChildNode(DATE_FIELD_ID);
        yearLabel = getChildNode(TOTAL_REVENUE_FIELD_ID);
    }

    public String getYear() {
        return totalYearlyRevenueLabel.getText();
    }

    public String getTotalRevenue() {
        return yearLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code yearlyRevenue}.
     */
    public boolean equals(YearlyRevenue yearlyRevenue) {
        return getTotalRevenue().equals(yearlyRevenue.getTotalRevenue())
                && getYear().equals(yearlyRevenue.getYear());
    }
}
