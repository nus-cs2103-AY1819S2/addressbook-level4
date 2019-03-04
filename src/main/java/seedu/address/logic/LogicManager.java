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
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BookingModel;
import seedu.address.model.CustomerModel;
import seedu.address.model.ReadOnlyAddressBook;
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
    private final AddressBookParser addressBookParser;
    private boolean addressBookModified;

    public LogicManager(CustomerModel customerModel, BookingModel bookingModel, Storage storage) {
        this.customerModel = customerModel;
        this.bookingModel = bookingModel;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        customerModel.getAddressBook().addListener(observable -> addressBookModified = true);
        bookingModel.getAddressBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText, customerModel, bookingModel);
            if (command instanceof CustomerCommand) {
                commandResult = command.execute(customerModel, history);
            } else {
                commandResult = command.execute(bookingModel, history);
            }
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(bookingModel.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return customerModel.getAddressBook();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return customerModel.getFilteredCustomerList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return customerModel.getAddressBookFilePath();
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
    public void setSelectedCustomer(Customer customer) {
        customerModel.setSelectedCustomer(customer);
    }
}
