package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Provides a handle for {@code HealthWorkerListPanel} containing the list of {@code HealthWorkerCard}.
 */
public class HealthWorkerListPanelHandle extends NodeHandle<ListView<HealthWorker>> {
    public static final String HEALTH_WORKER_LIST_VIEW_ID = "#healthWorkerListView";

    private static final String BORDER_PANE_ID = "#borderPane";

    private Optional<HealthWorker> lastRememberedSelectedHealthWorkerCard;

    public HealthWorkerListPanelHandle(ListView<HealthWorker> healthWorkerListPanelNode) {
        super(healthWorkerListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RequestCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public HealthWorkerCardHandle getHandleToSelectedCard() {
        List<HealthWorker> selectedHealthWorkerList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedHealthWorkerList.size() != 1) {
            throw new AssertionError("Health worker list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(HealthWorkerCardHandle::new)
                .filter(handle -> handle.equals(selectedHealthWorkerList.get(0)))
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
        List<HealthWorker> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the list view to display {@code healthWorker}.
     */
    public void navigateToCard(HealthWorker healthWorker) {
        if (!getRootNode().getItems().contains(healthWorker)) {
            throw new IllegalArgumentException("Health worker does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(healthWorker);
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
     * Selects the {@code HealthWorkerCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the card handle of a health worker associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public HealthWorkerCardHandle getHealthWorkerCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(HealthWorkerCardHandle::new)
                .filter(handle -> handle.equals(getHealthWorker(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private HealthWorker getHealthWorker(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the list view are definitely in the scene graph, while some nodes that are not
     * visible in the list view may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(BORDER_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code HealthWorkerCard} in the list.
     */
    public void rememberSelectedHealthWorkerCard() {
        List<HealthWorker> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedHealthWorkerCard = Optional.empty();
        } else {
            lastRememberedSelectedHealthWorkerCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code HealthWorkerCard} is different from the value remembered by
     * the most recent {@code rememberSelectedHealthWorkerCard()} call.
     */
    public boolean isSelectedHealthWorkerCardChanged() {
        List<HealthWorker> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedHealthWorkerCard.isPresent();
        } else {
            return !lastRememberedSelectedHealthWorkerCard.isPresent()
                    || !lastRememberedSelectedHealthWorkerCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
