package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CustomerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.HotelManagementSystemParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BookingModel;
import seedu.address.model.CustomerModel;
import seedu.address.model.ReadOnlyHotelManagementSystem;
import seedu.address.model.booking.Booking;
import seedu.address.model.customer.Customer;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final CustomerModel customerModel;
    private final BookingModel bookingModel;
    private final Storage storage;
    private final CommandHistory history;
    private final HotelManagementSystemParser hotelManagementSystemParser;
    private boolean hotelManagementSystemModified;

    public LogicManager(CustomerModel customerModel, BookingModel bookingModel, Storage storage) {
        this.customerModel = customerModel;
        this.bookingModel = bookingModel;
        this.storage = storage;
        history = new CommandHistory();
        hotelManagementSystemParser = new HotelManagementSystemParser();

        // Set hotelManagementSystemModified to true whenever the models' address book is modified.
        customerModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        bookingModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
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
            } else {
                commandResult = command.execute(bookingModel, history);
            }
        } finally {
            history.add(commandText);
        }

        if (hotelManagementSystemModified) {
            logger.info("Address book modified, saving to file.");
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
}
