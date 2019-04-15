package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.book.Review;

/**
 * The Browser Panel of the App.
 */
public class ReviewBrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    //private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label reviewMessage;

    @FXML
    private Label title;

    public ReviewBrowserPanel(ObservableValue<Review> selectedReview) {
        super(FXML);
        title.setText("Selected Review");

        reviewMessage.setWrapText(true);

        selectedReview.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                reviewMessage.setText("");
                return;
            }
            reviewMessage.setText(newValue.getReviewMessage());
        });

        reviewMessage.setText("");
    }
}
