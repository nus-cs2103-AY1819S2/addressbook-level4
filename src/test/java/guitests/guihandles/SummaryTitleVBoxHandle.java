package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.restaurant.Restaurant;

/**
 * Provides a handle to a restaurant card in the restaurant list panel.
 */
public class SummaryTitleVBoxHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String AVG_RATING_FIELD_ID = "#avgRating";
    private static final String TOTAL_VISITS_FIELD_ID = "#totalVisits";
    private static final String FIELD_NOT_ADDED = "N.A.";
    private static final String NO_AVG_RATING = "-1.0";

    private final Label nameLabel;
    private final Label avgRatingLabel;
    private final Label totalVisitsLabel;

    public SummaryTitleVBoxHandle(VBox vBox) {
        super(vBox);

        nameLabel = getChildNode(NAME_FIELD_ID);
        avgRatingLabel = getChildNode(AVG_RATING_FIELD_ID);
        totalVisitsLabel = getChildNode(TOTAL_VISITS_FIELD_ID);
    }

    public String getAvgRating() {
        String label = avgRatingLabel.getText();
        String avgRating;

        if (label.equals(FIELD_NOT_ADDED)) {
            avgRating = NO_AVG_RATING;
        } else {
            avgRating = label;
        }

        return avgRating;
    }

    public String getTotalVisits() {
        return totalVisitsLabel.getText();
    }

    public String getRatingVisits() {
        String ratingVisits = getAvgRating() + " " + getTotalVisits();

        return ratingVisits;
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code restaurant}.
     */
    public boolean equals(Restaurant restaurant) {
        return getName().equals(restaurant.getName().fullName)
                && getRatingVisits().equals(restaurant.getSummary().toString());
    }
}
