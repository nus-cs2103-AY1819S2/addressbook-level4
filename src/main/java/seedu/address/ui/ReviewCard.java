package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.review.Review;

/**
 * An UI component that displays information of a {@code Review}.
 */
public class ReviewCard extends UiPart<Region> {

    private static final String FXML = "ReviewListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/fooddiary-level4/issues/336">The issue on FoodDiary level 4</a>
     */

    public final Review review;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label timestamp;
    @FXML
    private Label rating;
    @FXML
    private Label entry;

    public ReviewCard(Review review, int displayedIndex) {
        super(FXML);
        this.review = review;
        id.setText(displayedIndex + ". ");
        timestamp.setText(review.getTimeStamp().toLocalDateTime().toString());
        rating.setText(review.getRating().value + " / 5.0");
        entry.setText(review.getEntry().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReviewCard)) {
            return false;
        }

        // state check
        ReviewCard card = (ReviewCard) other;
        return id.getText().equals(card.id.getText())
                && review.equals(card.review);
    }
}
