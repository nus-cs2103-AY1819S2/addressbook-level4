package seedu.hms.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.reservation.Reservation;

/**
 * Panel containing the list of reservations.
 * Note: Currently this shows the list of bookings instead of reservation
 * This will be solved in v1.3
 */
public class ReservationListPanel extends UiPart<Region> {
    private static final String FXML = "ReservationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Reservation> reservationListView;

    public ReservationListPanel(ObservableList<Reservation> reservationList,
                                ObservableValue<Reservation> selectedReservation,
                                Consumer<Reservation> onSelectedReservationChange) {
        super(FXML);
        reservationListView.setItems(reservationList);
        reservationListView.setCellFactory(listView -> new ReservationListViewCell());
        reservationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in reservation list panel changed to : '" + newValue + "'");
            onSelectedReservationChange.accept(newValue);
        });
        selectedReservation.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected reservation changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected reservation,
            // otherwise we would have an infinite loop.
            if (Objects.equals(reservationListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                reservationListView.getSelectionModel().clearSelection();
            } else {
                int index = reservationListView.getItems().indexOf(newValue);
                reservationListView.scrollTo(index);
                reservationListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Booking} using a {@code BookingCard}.
     */
    class ReservationListViewCell extends ListCell<Reservation> {
        @Override
        protected void updateItem(Reservation reservation, boolean empty) {
            super.updateItem(reservation, empty);

            if (empty || reservation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReservationCard(reservation, getIndex() + 1).getRoot());
            }
        }
    }

}
