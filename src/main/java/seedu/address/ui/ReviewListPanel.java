package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Review;

public class ReviewListPanel extends UiPart<Region>  {
    private static final String FXML = "ReviewListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReviewListPanel.class);

    @FXML
    private ListView<Review> reviewListView;

    public ReviewListPanel(ObservableList<Review> reviewList, ObservableValue<Review> selectedReview,
                           Consumer<Review> onSelectedReviewChange) {
        super(FXML);
        reviewListView.setItems(reviewList);
        reviewListView.setCellFactory(listView -> new ReviewListViewCell());
        reviewListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in review list panel changed to : '" + newValue + "'");
            onSelectedReviewChange.accept(newValue);
        });
        selectedReview.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected review changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected review,
            // otherwise we would have an infinite loop.
            if (Objects.equals(reviewListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                reviewListView.getSelectionModel().clearSelection();
            } else {
                int index = reviewListView.getItems().indexOf(newValue);
                reviewListView.scrollTo(index);
                reviewListView.getSelectionModel().clearAndSelect(index);
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
