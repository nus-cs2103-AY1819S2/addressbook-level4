package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.DailyRevenue;

/**
 * An UI component that displays information of a {@code Monthly Statistics}.
 */
public class DailyStatisticsCard extends UiPart<Region>{

    private static final String FXML = "DailyStatisticsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final DailyRevenue dailyRevenue;

    @FXML
    private Label day;
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private Label totalDailyRevenue;

    public DailyStatisticsCard(DailyRevenue dailyRevenue) {
        super(FXML);
        this.dailyRevenue = dailyRevenue;

        day.setText("" + this.dailyRevenue.getDay());
        month.setText("" + this.dailyRevenue.getMonth());
        year.setText("" + this.dailyRevenue.getYear());
        totalDailyRevenue.setText("" + this.dailyRevenue.getTotalDailyRevenue());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DailyStatisticsCard)) {
            return false;
        }

        // state check
        DailyStatisticsCard card = (DailyStatisticsCard) other;
        return day.equals(card.day) && month.equals(card.month) && year.equals(card.year)
                && totalDailyRevenue.equals(card.totalDailyRevenue);
    }
}
