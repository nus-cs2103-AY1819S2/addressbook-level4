package seedu.hms.ui;

import java.util.Objects;
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

    @FXML
    private StackPane bookingListPanelPlaceholder;

    @FXML
    private StackPane reservationListPanelPlaceholder;

    @FXML
    private StackPane billPanelPlaceholder;

    @FXML
    private TabPane tabPane;

    public BookingAndReservationPanel(BookingListPanel bookingListPanel, ReservationListPanel reservationListPanel,
                                      BillPanel billPanel,
                                      ObservableValue<Integer> selectedTabIndex,
                                      Consumer<Integer> onSelectedTabIndexChange) {
        super(FXML);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            onSelectedTabIndexChange.accept(newValue.intValue());
        });
        selectedTabIndex.addListener((observable, oldValue, newValue) -> {
            // Don't modify selection if we are already selecting the selected booking,
            // otherwise we would have an infinite loop.
            if (Objects.equals(tabPane.getSelectionModel().getSelectedIndex(), newValue.intValue())) {
                return;
            }

            tabPane.getSelectionModel().select(newValue);
        });
        bookingListPanelPlaceholder.getChildren().add(bookingListPanel.getRoot());
        reservationListPanelPlaceholder.getChildren().add(reservationListPanel.getRoot());
        billPanelPlaceholder.getChildren().add(billPanel.getRoot());
    }

}
