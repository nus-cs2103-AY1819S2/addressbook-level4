package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.customer.Customer;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of customers
     */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Returns an unmodifiable view of the filtered list of bookings
     */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected customer in the filtered customer list.
     * null if no customer is selected.
     *
     * @see seedu.address.model.CustomerModel#selectedCustomerProperty()
     */
    ReadOnlyProperty<Customer> selectedCustomerProperty();

    /**
     * Selected bookings in the filtered booking list.
     * null if no booking is selected.
     *
     * @see seedu.address.model.CustomerModel#selectedCustomerProperty()
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Sets the selected customer in the filtered customer list.
     *
     * @see seedu.address.model.CustomerModel#setSelectedCustomer(Customer)
     */
    void setSelectedCustomer(Customer customer);

    /**
     * Sets the selected customer in the filtered customer list.
     *
     * @see seedu.address.model.BookingModel#setSelectedBooking(Booking)
     */
    void setSelectedBooking(Booking booking);
}
