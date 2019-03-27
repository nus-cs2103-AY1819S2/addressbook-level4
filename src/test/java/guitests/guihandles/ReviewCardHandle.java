package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.book.Review;

/**
 * Provides a handle to a review card in the person list panel.
 */
public class ReviewCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String BOOKNAME_FIELD_ID = "#bookName";
    private static final String MESSAGE_FIELD_ID = "#reviewMessage";
    private static final String DATE_FIELD_ID = "#dateCreated";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label bookNameLabel;
    private final Label messageLabel;
    private final Label dateLabel;

    public ReviewCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        bookNameLabel = getChildNode(BOOKNAME_FIELD_ID);
        messageLabel = getChildNode(MESSAGE_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getBookName() {
        return bookNameLabel.getText();
    }

    public String getMessage() {
        return messageLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code review}.
     */
    public boolean equals(Review review) {
        return getName().equals(review.getTitle().fullName)
                && getBookName().equals(review.getBookName().fullName)
                && getMessage().equals(review.getReviewMessage())
                && getDate().equals(review.getDateCreated());
    }
}
