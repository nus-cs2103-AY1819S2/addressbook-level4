package seedu.hms.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.logic.commands.Command;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.CustomerCommand;
import seedu.hms.logic.commands.ReservationCommand;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.HotelManagementSystemParser;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final CustomerModel customerModel;
    private final BookingModel bookingModel;
    private final ReservationModel reservationModel;
    private final Storage storage;
    private final CommandHistory history;
    private final HotelManagementSystemParser hotelManagementSystemParser;
    private boolean hotelManagementSystemModified;

    public LogicManager(CustomerModel customerModel, BookingModel bookingModel, ReservationModel reservationModel,
                        Storage storage) {
        this.customerModel = customerModel;
        this.bookingModel = bookingModel;
        this.reservationModel = reservationModel
        this.storage = storage;
        history = new CommandHistory();
        hotelManagementSystemParser = new HotelManagementSystemParser();

        // Set hotelManagementSystemModified to true whenever the models' hms book is modified.
        customerModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        bookingModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        reservationModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        hotelManagementSystemModified = false;

        CommandResult commandResult;
        try {
            Command command = hotelManagementSystemParser.parseCommand(commandText, customerModel, bookingModel);
            if (command instanceof CustomerCommand) {
                commandResult = command.execute(customerModel, history);
            } else if (command instanceof ReservationCommand) {
                commandResult = command.execute(reservationModel, history);
            } else {
                commandResult = command.execute(bookingModel, history);
            }
        } finally {
            history.add(commandText);
        }

        if (hotelManagementSystemModified) {
            logger.info("hms book modified, saving to file.");
            try {
                storage.saveHotelManagementSystem(bookingModel.getHotelManagementSystem());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
        return customerModel.getHotelManagementSystem();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return customerModel.getFilteredCustomerList();
    }

    @Override
    public ObservableList<Booking> getFilteredBookingList() {
        return bookingModel.getFilteredBookingList();
    }

    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return reservationModel.getFilteredReservationList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getHotelManagementSystemFilePath() {
        return customerModel.getHotelManagementSystemFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return customerModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        customerModel.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Customer> selectedCustomerProperty() {
        return customerModel.selectedCustomerProperty();
    }

    @Override
    public ReadOnlyProperty<Booking> selectedBookingProperty() {
        return bookingModel.selectedBookingProperty();
    }

    @Override
    public void setSelectedCustomer(Customer customer) {
        customerModel.setSelectedCustomer(customer);
    }

    @Override
    public void setSelectedBooking(Booking booking) {
        bookingModel.setSelectedBooking(booking);
    }

    @Override
    public void setSelectedReservation(Reservation reservation) {
        reservationModel.setSelectedReservation(reservation);
    }
}
