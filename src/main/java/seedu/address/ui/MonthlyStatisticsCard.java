package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.MonthlyRevenue;

/**
 * An UI component that displays information of a {@code Monthly Statistics}.
 */
public class MonthlyStatisticsCard extends UiPart<Region> {

    private static final String FXML = "MonthlyStatisticsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final MonthlyRevenue monthlyRevenue;

    @FXML
    private HBox monthlyStatisticsCardPane;
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private Label totalMonthlyRevenue;

    private final ArrayList<String> monthsInWords = new
            ArrayList<>(List.of("JAN ", "FEB ", "MAR ", "APR ", "MAY ", "JUN ", "JUL ", "AUG ", "SEP ", "OCT ", "NOV ",
            "DEC "));

    public MonthlyStatisticsCard(MonthlyRevenue monthlyRevenue) {
        super(FXML);
        this.monthlyRevenue = monthlyRevenue;

        month.setText(monthsInWords.get(Integer.parseInt(monthlyRevenue.getMonth().toString()) - 1));
        year.setText(this.monthlyRevenue.getYear().toString());
        totalMonthlyRevenue.setText("$ " + String.format("%.2f", this.monthlyRevenue.getTotalRevenue()));

        if (monthlyRevenue.getTotalRevenue() == 0) {
            monthlyStatisticsCardPane.setStyle("-fx-background-color: #1d1d1d;");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthlyStatisticsCard)) {
            return false;
        }

        // state check
        MonthlyStatisticsCard card = (MonthlyStatisticsCard) other;
        return month.equals(card.month) && year.equals(card.year)
                && totalMonthlyRevenue.equals(card.totalMonthlyRevenue);
    }
}
