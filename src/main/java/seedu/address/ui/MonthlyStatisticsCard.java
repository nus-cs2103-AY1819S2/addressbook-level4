package seedu.address.ui;

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
    private HBox cardPane;
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private Label totalMonthlyRevenue;

    public MonthlyStatisticsCard(MonthlyRevenue monthlyRevenue) {
        super(FXML);
        this.monthlyRevenue = monthlyRevenue;

        switch(Integer.parseInt(monthlyRevenue.getMonth().toString())) {
            case 1:
                month.setText("JAN ");
                break;
            case 2:
                month.setText("FEB ");
                break;
            case 3:
                month.setText("MAR ");
                break;
            case 4:
                month.setText("APR ");
                break;
            case 5:
                month.setText("MAY ");
                break;
            case 6:
                month.setText("JUN ");
                break;
            case 7:
                month.setText("JUL ");
                break;
            case 8:
                month.setText("AUG ");
                break;
            case 9:
                month.setText("SEP ");
                break;
            case 10:
                month.setText("OCT ");
                break;
            case 11:
                month.setText("NOV ");
                break;
            case 12:
                month.setText("DEC ");
        }
        year.setText(this.monthlyRevenue.getYear().toString());
        totalMonthlyRevenue.setText("$ " + String.format("%.2f", this.monthlyRevenue.getTotalMonthlyRevenue()));
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
