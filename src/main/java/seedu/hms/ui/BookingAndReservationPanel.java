package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.hms.model.booking.Booking;

/**
 * Panel containing the list of bookings.
 */
public class BookingAndReservationPanel extends UiPart<Region> {
    private static final String FXML = "BookingAndReservationPanel.fxml";

    @FXML
    private ListView<Booking> bookingListView;

    @FXML
    private StackPane bookingListPanelPlaceholder;

    @FXML
    private StackPane reservationListPanelPlaceholder;

    public BookingAndReservationPanel(BookingListPanel bookingListPanel, ReservationListPanel reservationListPanel) {
        super(FXML);
        bookingListPanelPlaceholder.getChildren().add(bookingListPanel.getRoot());
        reservationListPanelPlaceholder.getChildren().add(reservationListPanel.getRoot());
    }
}
