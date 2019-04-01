package seedu.hms.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Panel containing the list of bookings, reservations and billings.
 */
public class BookingAndReservationPanel extends UiPart<Region> {
    private static final String FXML = "BookingAndReservationPanel.fxml";
    private final Consumer<Integer> onSelectedTabChange;

    @FXML
    private StackPane bookingListPanelPlaceholder;

    @FXML
    private StackPane reservationListPanelPlaceholder;

    @FXML
    private TabPane tabPane;

    public BookingAndReservationPanel(BookingListPanel bookingListPanel, ReservationListPanel reservationListPanel,
                                      ObservableValue<Integer> selectedTabIndex,
                                      Consumer<Integer> onSelectedTabChange) {
        super(FXML);
        System.out.println(tabPane);
        selectedTabIndex.addListener((observable, oldValue, newValue) -> {
            tabPane.getSelectionModel().select(newValue);
        });
        this.onSelectedTabChange = onSelectedTabChange;
        bookingListPanelPlaceholder.getChildren().add(bookingListPanel.getRoot());
        reservationListPanelPlaceholder.getChildren().add(reservationListPanel.getRoot());
    }

}
