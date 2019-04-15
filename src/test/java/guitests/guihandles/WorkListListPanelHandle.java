package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.equipment.model.WorkList;

/**
 * Provides a handle for {@code WorkListPanel} containing the list of {@code WorkListCard}.
 */
public class WorkListListPanelHandle extends NodeHandle<ListView<WorkList>> {
    public static final String WORKLIST_LIST_VIEW_ID = "#workListListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<WorkList> lastRememberedSelectedWorkListCard;

    public WorkListListPanelHandle(ListView<WorkList> workListPanelNode) {
        super(workListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code WorkListCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkListCardHandle getHandleToSelectedCard() {
        List<WorkList> selectedWorkListList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedWorkListList.size() != 1) {
            throw new AssertionError("WorkList list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(WorkListCardHandle::new)
                .filter(handle -> handle.equals(selectedWorkListList.get(0)))
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
        List<WorkList> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code workList}.
     */
    public void navigateToCard(WorkList workList) {
        if (!getRootNode().getItems().contains(workList)) {
            throw new IllegalArgumentException("Equipment does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(workList);
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
     * Selects the {@code WorkListCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the WorkList card handle of a WorkList associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkListCardHandle getWorkListCardHandle(int index) {
        System.out.println(index);
        System.out.println(getWorkList(index).getAssignee());
        return getAllCardNodes().stream()
                .map(WorkListCardHandle::new)
                .filter(handle -> handle.equals(getWorkList(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private WorkList getWorkList(int index) {
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
     * Remembers the selected {@code WorkListCard} in the list.
     */
    public void rememberSelectedWorklistCard() {
        List<WorkList> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedWorkListCard = Optional.empty();
        } else {
            lastRememberedSelectedWorkListCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code WorkListCard} is different from the value remembered by the most recent
     * {@code rememberSelectedWorkListCard()} call.
     */
    public boolean isSelectedWorkListCardChanged() {
        List<WorkList> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedWorkListCard.isPresent();
        } else {
            return !lastRememberedSelectedWorkListCard.isPresent()
                    || !lastRememberedSelectedWorkListCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
