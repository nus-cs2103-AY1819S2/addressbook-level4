package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.YearlyRevenue;

/**
 * An UI component that displays information of a {@code Yearly Statistics}.
 */
public class YearlyStatisticsCard extends UiPart<Region> {

    private static final String FXML = "YearlyStatisticsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final YearlyRevenue yearlyRevenue;

    @FXML
    private HBox cardPane;
    @FXML
    private Label year;
    @FXML
    private Label totalYearlyRevenue;

    public YearlyStatisticsCard(YearlyRevenue yearlyRevenue) {
        super(FXML);
        this.yearlyRevenue = yearlyRevenue;

        year.setText(this.yearlyRevenue.getYear().toString());
        totalYearlyRevenue.setText("$ " + this.yearlyRevenue.getTotalYearlyRevenue());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof YearlyStatisticsCard)) {
            return false;
        }

        // state check
        YearlyStatisticsCard card = (YearlyStatisticsCard) other;
        return year.equals(card.year) && totalYearlyRevenue.equals(card.totalYearlyRevenue);
    }
}
