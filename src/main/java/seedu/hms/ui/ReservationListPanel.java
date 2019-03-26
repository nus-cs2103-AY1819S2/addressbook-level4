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
import seedu.hms.model.booking.Booking;

/**
 * Panel containing the list of reservations.
 * Note: Currently this shows the list of bookings instead of reservation
 * This will be solved in v1.3
 */
public class ReservationListPanel extends UiPart<Region> {
    private static final String FXML = "BookingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Booking> bookingListView;

    public ReservationListPanel(ObservableList<Booking> bookingList, ObservableValue<Booking> selectedBooking,
                                Consumer<Booking> onSelectedBookingChange) {
        super(FXML);
        bookingListView.setItems(bookingList);
        bookingListView.setCellFactory(listView -> new BookingListViewCell());
        bookingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in booking list panel changed to : '" + newValue + "'");
            onSelectedBookingChange.accept(newValue);
        });
        selectedBooking.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected booking changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected booking,
            // otherwise we would have an infinite loop.
            if (Objects.equals(bookingListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                bookingListView.getSelectionModel().clearSelection();
            } else {
                int index = bookingListView.getItems().indexOf(newValue);
                bookingListView.scrollTo(index);
                bookingListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Booking} using a {@code BookingCard}.
     */
    class BookingListViewCell extends ListCell<Booking> {
        @Override
        protected void updateItem(Booking booking, boolean empty) {
            super.updateItem(booking, empty);

            if (empty || booking == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BookingCard(booking, getIndex() + 1).getRoot());
            }
        }
    }

}
