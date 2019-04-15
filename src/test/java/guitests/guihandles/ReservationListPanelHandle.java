package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.hms.model.reservation.Reservation;

/**
 * Provides a handle for {@code ReservationListPanel} containing the list of {@code ReservationCard}.
 */
public class ReservationListPanelHandle extends NodeHandle<ListView<Reservation>> {
    public static final String RESERVATION_LIST_VIEW_ID = "#reservationListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Reservation> lastRememberedSelectedReservationCard;

    public ReservationListPanelHandle(ListView<Reservation> reservationListPanelNode) {
        super(reservationListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ReservationListCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ReservationCardHandle getHandleToSelectedCard() {
        List<Reservation> selectedReservationList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedReservationList.size() != 1) {
            throw new AssertionError("Reservation list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ReservationCardHandle::new)
                .filter(handle -> handle.equals(selectedReservationList.get(0)))
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
        List<Reservation> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code reservation}.
     */
    public void navigateToCard(Reservation reservation) {
        if (!getRootNode().getItems().contains(reservation)) {
            throw new IllegalArgumentException("Reservation does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(reservation);
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
     * Selects the {@code ReservationCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the reservation card handle of a reservation associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ReservationCardHandle getReservationCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ReservationCardHandle::new)
                .filter(handle -> handle.equals(getReservation(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Reservation getReservation(int index) {
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
     * Remembers the selected {@code BookingCard} in the list.
     */
    public void rememberSelectedReservationCard() {
        List<Reservation> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedReservationCard = Optional.empty();
        } else {
            lastRememberedSelectedReservationCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ReservationCard} is different from the value remembered by the most recent
     * {@code rememberSelectedReservationCard()} call.
     */
    public boolean isSelectedReservationCardChanged() {
        List<Reservation> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedReservationCard.isPresent();
        } else {
            return !lastRememberedSelectedReservationCard.isPresent()
                    || !lastRememberedSelectedReservationCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
