package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.book.Review;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class ReviewListPanelHandle extends NodeHandle<ListView<Review>> {
    public static final String REVIEW_LIST_VIEW_ID = "#reviewListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Review> lastRememberedSelectedReviewCard;

    public ReviewListPanelHandle(ListView<Review> reviewListPanelNode) {
        super(reviewListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ReviewCardHandle getHandleToSelectedCard() {
        List<Review> selectedReviewList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedReviewList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ReviewCardHandle::new)
                .filter(handle -> handle.equals(selectedReviewList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Review> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code person}.
     */
    public void navigateToCard(Review review) {
        if (!getRootNode().getItems().contains(review)) {
            throw new IllegalArgumentException("Person does not exist.");
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
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
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
     * Remembers the selected {@code ReviewCard} in the list.
     */
    public void rememberSelectedReviewCard() {
        List<Review> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedReviewCard = Optional.empty();
        } else {
            lastRememberedSelectedReviewCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedReviewCardChanged() {
        List<Review> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedReviewCard.isPresent();
        } else {
            return !lastRememberedSelectedReviewCard.isPresent()
                    || !lastRememberedSelectedReviewCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
