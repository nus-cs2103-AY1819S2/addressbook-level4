package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.travel.model.place.Place;

/**
 * Provides a handle for {@code PlaceListPanel} containing the list of {@code PlaceCard}.
 */
public class PlaceListPanelHandle extends NodeHandle<ListView<Place>> {
    public static final String PLACE_LIST_VIEW_ID = "#placeListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Place> lastRememberedSelectedPlaceCard;

    public PlaceListPanelHandle(ListView<Place> placeListPanelNode) {
        super(placeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PlaceCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PlaceCardHandle getHandleToSelectedCard() {
        List<Place> selectedPlaceList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPlaceList.size() != 1) {
            throw new AssertionError("Place list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PlaceCardHandle::new)
                .filter(handle -> handle.equals(selectedPlaceList.get(0)))
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
        List<Place> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code place}.
     */
    public void navigateToCard(Place place) {
        if (!getRootNode().getItems().contains(place)) {
            throw new IllegalArgumentException("Place does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(place);
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
     * Selects the {@code PlaceCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the place card handle of a place associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PlaceCardHandle getPlaceCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PlaceCardHandle::new)
                .filter(handle -> handle.equals(getPlace(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Place getPlace(int index) {
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
     * Remembers the selected {@code PlaceCard} in the list.
     */
    public void rememberSelectedPlaceCard() {
        List<Place> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPlaceCard = Optional.empty();
        } else {
            lastRememberedSelectedPlaceCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PlaceCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPlaceCard()} call.
     */
    public boolean isSelectedPlaceCardChanged() {
        List<Place> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPlaceCard.isPresent();
        } else {
            return !lastRememberedSelectedPlaceCard.isPresent()
                    || !lastRememberedSelectedPlaceCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
