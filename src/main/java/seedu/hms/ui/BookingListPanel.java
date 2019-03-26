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
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.customer.Customer;

/**
 * Panel containing the list of bookings.
 */
public class BookingListPanel extends UiPart<Region> {
    private static final String FXML = "BookingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookingListPanel.class);

    @FXML
    private ListView<Booking> bookingListView;

    public BookingListPanel(ObservableList<Booking> bookingList, ObservableValue<Booking> selectedBooking,
                            Consumer<Booking> onSelectedBookingChange, ObservableValue<Customer> selectedCustomer,
                            ObservableValue<ServiceType> selectedServiceType,
                            CommandBox.CommandExecutor commandExecutor) {
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
        selectedCustomer.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected customer changed to: " + newValue);


            if (newValue != null) {
                try {
                    commandExecutor.execute("fb id/" + newValue.getIdNum().toString());
                } catch (CommandException | ParseException e) {
                    return;
                }
            }
        });
        selectedServiceType.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected serviceType changed to: " + newValue);


            if (newValue != null) {
                try {
                    commandExecutor.execute("fb s/" + newValue.getName().toString());
                } catch (CommandException | ParseException e) {
                    return;
                }
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
