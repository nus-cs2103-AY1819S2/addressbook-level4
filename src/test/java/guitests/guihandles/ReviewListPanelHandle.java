package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.review.Review;

/**
 * Provides a handle for {@code ReviewListPanel} containing the list of {@code ReviewCard}.
 */
public class ReviewListPanelHandle extends NodeHandle<ListView<Review>> {
    public static final String REVIEW_LIST_VIEW_ID = "#reviewListView";

    private static final String CARD_PANE_ID = "#cardPane";

    public ReviewListPanelHandle(ListView<Review> reviewListPanelNode) {
        super(reviewListPanelNode);
    }

    /**
     * Navigates the listview to display {@code restaurant}.
     */
    public void navigateToCard(Review review) {
        if (!getRootNode().getItems().contains(review)) {
            throw new IllegalArgumentException("Review does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(review);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code ReviewCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the review card handle of a review associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ReviewCardHandle getReviewCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ReviewCardHandle::new)
                .filter(handle -> handle.equals(getReview(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Review getReview(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
