package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Review;

public class ReviewCard extends UiPart<Region> {

    private static final String FXML = "ReviewListCard.fxml";

    private static final String[] TAG_COLOR_STYLES =
            { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on BookShelf level 4</a>
     */

    public final Review review;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label bookName;
    @FXML
    private Label reviewMessage;
    @FXML
    private FlowPane dateCreated;

    public ReviewCard(Review review, int displayedIndex) {
        super(FXML);
        this.review = review;
        id.setText(displayedIndex + ". ");
        name.setText(review.getTitle().fullName);
        bookName.setText(review.getBookName().fullName);
        reviewMessage.setText(review.getReviewMessage());
        initDate(review);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code review}.
     */
    private void initDate(Review review) {
        Label tagLabel = new Label(review.getDateCreated());
        tagLabel.getStyleClass().add(getTagColorStyleFor(review.getDateCreated()));
        dateCreated.getChildren().add(tagLabel);
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
