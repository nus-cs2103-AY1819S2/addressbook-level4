package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * Provides a handle for {@code ModuleTakenListPanel} containing the list of {@code ModuleTakenCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<ModuleTaken>> {
    public static final String PERSON_LIST_VIEW_ID = "#moduleTakenListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<ModuleTaken> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<ModuleTaken> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<ModuleTaken> selectedModuleTakenList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedModuleTakenList.size() != 1) {
            throw new AssertionError("ModuleTaken list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PersonCardHandle::new)
                .filter(handle -> handle.equals(selectedModuleTakenList.get(0)))
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
        List<ModuleTaken> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code moduleTaken}.
     */
    public void navigateToCard(ModuleTaken moduleTaken) {
        if (!getRootNode().getItems().contains(moduleTaken)) {
            throw new IllegalArgumentException("ModuleTaken does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(moduleTaken);
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
     * Selects the {@code ModuleTakenCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the moduleTaken card handle of a moduleTaken associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PersonCardHandle::new)
                .filter(handle -> handle.equals(getPerson(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private ModuleTaken getPerson(int index) {
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
     * Remembers the selected {@code ModuleTakenCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<ModuleTaken> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ModuleTakenCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<ModuleTaken> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
