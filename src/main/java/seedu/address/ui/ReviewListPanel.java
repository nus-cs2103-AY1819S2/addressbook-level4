package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;

/**
 * Panel containing the list of reviews belonging to a review.
 */
public class ReviewListPanel extends UiPart<Region> {
    private static final String FXML = "ReviewListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReviewListPanel.class);

    @FXML
    private ListView<Review> reviewListView;

    public ReviewListPanel(ObservableValue<Restaurant> selectedRestaurant) {
        super(FXML);

        selectedRestaurant.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected review changed to: " + newValue);

            if (newValue == null) {
                // TODO: Need to remove? Because reviewListView won't be selected
                reviewListView.getSelectionModel().clearSelection();
            } else {
                ObservableList<Review> reviewList = FXCollections.observableArrayList();
                reviewList.addAll(newValue.getReviews());

                reviewListView.setItems(reviewList);
                reviewListView.setCellFactory(listView -> new ReviewListViewCell());
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Review} using a {@code ReviewCard}.
     */
    class ReviewListViewCell extends ListCell<Review> {
        @Override
        protected void updateItem(Review review, boolean empty) {
            super.updateItem(review, empty);

            if (empty || review == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReviewCard(review, getIndex() + 1).getRoot());
            }
        }
    }

}
