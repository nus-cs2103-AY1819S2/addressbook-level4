package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.request.Request;

/**
 * Provides a handle for {@code RequestListPanel} containing the list of {@code RequestCard}.
 */
public class RequestListPanelHandle extends NodeHandle<ListView<Request>> {
    public static final String REQUEST_LIST_VIEW_ID = "#requestListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Request> lastRememberedSelectedRequestCard;

    public RequestListPanelHandle(ListView<Request> requestListPanelNode) {
        super(requestListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RequestCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RequestCardHandle getHandleToSelectedCard() {
        List<Request> selectedRequestList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedRequestList.size() != 1) {
            throw new AssertionError("Request list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(RequestCardHandle::new)
                .filter(handle -> handle.equals(selectedRequestList.get(0)))
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
        List<Request> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the list view to display {@code request}.
     */
    public void navigateToCard(Request request) {
        if (!getRootNode().getItems().contains(request)) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(request);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the list view to {@code index}.
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
     * Selects the {@code RequestCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the card handle of a request associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RequestCardHandle getRequestCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(RequestCardHandle::new)
                .filter(handle -> handle.equals(getRequest(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Request getRequest(int index) {
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
     * Remembers the selected {@code RequestCard} in the list.
     */
    public void rememberSelectedRequestCard() {
        List<Request> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRequestCard = Optional.empty();
        } else {
            lastRememberedSelectedRequestCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedRequestCardChanged() {
        List<Request> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRequestCard.isPresent();
        } else {
            return !lastRememberedSelectedRequestCard.isPresent()
                    || !lastRememberedSelectedRequestCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
