package guitests.guihandles;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.review.Review;

/**
 * Provides a handle to a review card in the review list panel.
 */
public class ReviewCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TIMESTAMP_FIELD_ID = "#timestamp";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String ENTRY_FIELD_ID = "#entry";
    private static final SimpleDateFormat TIMESTAMP = new SimpleDateFormat("EEE, d MMM yyyy, h.mm aa");
    private static final DecimalFormat ONE_DP = new DecimalFormat("0.0");

    private final Label idLabel;
    private final Label timestampLabel;
    private final Label ratingLabel;
    private final Label entryLabel;

    public ReviewCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        timestampLabel = getChildNode(TIMESTAMP_FIELD_ID);
        ratingLabel = getChildNode(RATING_FIELD_ID);
        entryLabel = getChildNode(ENTRY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTimestamp() {
        return timestampLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public String getEntry() {
        return entryLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code review}.
     */
    public boolean equals(Review review) {
        return getTimestamp().equals(TIMESTAMP.format(review.getTimeStamp()))
                && getRating().equals(ONE_DP.format(review.getRating().toFloat()) + " / 5.0")
                && getEntry().equals(review.getEntry().toString());
    }
}
