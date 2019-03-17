package seedu.hms.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.booking.Booking;

/**
 * Panel containing the list of bookings.
 */
public class BookingAndReservationPanel extends UiPart<Region> {
    private static final String FXML = "BookingAndReservationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookingAndReservationPanel.class);
    private BookingListPanel bookingListPanel;
    private ReservationListPanel reservationListPanel;

    @FXML
    private ListView<Booking> bookingListView;

    @FXML
    private StackPane bookingListPanelPlaceholder;

    @FXML
    private StackPane reservationListPanelPlaceholder;

    public BookingAndReservationPanel(BookingListPanel bookingListPanel, ReservationListPanel reservationListPanel) {
        super(FXML);
        this.bookingListPanel = bookingListPanel;
        this.reservationListPanel = reservationListPanel;

        bookingListPanelPlaceholder.getChildren().add(bookingListPanel.getRoot());
        reservationListPanelPlaceholder.getChildren().add(reservationListPanel.getRoot());
    }
}
