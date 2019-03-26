package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.statistics.DailyRevenue;

/**
 * An UI component that displays information of a {@code Daily Statistics}.
 */
public class DailyStatisticsCard extends UiPart<Region> {

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
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label totalDailyRevenue;

    public DailyStatisticsCard(DailyRevenue dailyRevenue) {
        super(FXML);
        this.dailyRevenue = dailyRevenue;

        switch(Integer.parseInt(this.dailyRevenue.getMonth().toString())) {

        case 1:
            date.setText(this.dailyRevenue.getDay().toString() + "-Jan-" + this.dailyRevenue.getYear().toString());
            break;
        case 2:
            date.setText(this.dailyRevenue.getDay().toString() + "-Feb-" + this.dailyRevenue.getYear().toString());
            break;
        case 3:
            date.setText(this.dailyRevenue.getDay().toString() + "-Mar-" + this.dailyRevenue.getYear().toString());
            break;
        case 4:
            date.setText(this.dailyRevenue.getDay().toString() + "-Apr-" + this.dailyRevenue.getYear().toString());
            break;
        case 5:
            date.setText(this.dailyRevenue.getDay().toString() + "-May-" + this.dailyRevenue.getYear().toString());
            break;
        case 6:
            date.setText(this.dailyRevenue.getDay().toString() + "-Jun-" + this.dailyRevenue.getYear().toString());
            break;
        case 7:
            date.setText(this.dailyRevenue.getDay().toString() + "-Jul-" + this.dailyRevenue.getYear().toString());
            break;
        case 8:
            date.setText(this.dailyRevenue.getDay().toString() + "-Aug-" + this.dailyRevenue.getYear().toString());
            break;
        case 9:
            date.setText(this.dailyRevenue.getDay().toString() + "-Sep-" + this.dailyRevenue.getYear().toString());
            break;
        case 10:
            date.setText(this.dailyRevenue.getDay().toString() + "-Oct-" + this.dailyRevenue.getYear().toString());
            break;
        case 11:
            date.setText(this.dailyRevenue.getDay().toString() + "-Nov-" + this.dailyRevenue.getYear().toString());
            break;
        case 12:
            date.setText(this.dailyRevenue.getDay().toString() + "-Dec-" + this.dailyRevenue.getYear().toString());
        default:
            date.setText(this.dailyRevenue.getDay().toString() + this.dailyRevenue.getMonth().toString()
                    + this.dailyRevenue.getYear().toString());
        }

        totalDailyRevenue.setText("$" + String.format("%.2f", this.dailyRevenue.getTotalDailyRevenue()));
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
        return date.equals(card.date) && totalDailyRevenue.equals(card.totalDailyRevenue);
    }
}
