package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

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
    private HBox dailyStatisticsCardPane;
    @FXML
    private Label date;
    @FXML
    private Label totalDailyRevenue;

    private final ArrayList<String> monthsInWords = new
            ArrayList<>(List.of("-Jan-", "-Feb-", "-Mar-", "-Apr-", "-May-", "-Jun-", "-Jul-", "-Aug-",
            "-Sep-", "-Oct-", "-Nov-", "-Dec-"));

    public DailyStatisticsCard(DailyRevenue dailyRevenue) {
        super(FXML);
        this.dailyRevenue = dailyRevenue;

        date.setText(this.dailyRevenue.getDay().toString()
                + monthsInWords.get(Integer.parseInt(this.dailyRevenue.getMonth().toString()) - 1)
                + this.dailyRevenue.getYear().toString());

        totalDailyRevenue.setText("$" + String.format("%.2f", this.dailyRevenue.getTotalRevenue()));

        if (dailyRevenue.getTotalRevenue() == 0) {
            dailyStatisticsCardPane.setStyle("-fx-background-color: #1d1d1d;");
        }
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
