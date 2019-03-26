package seedu.hms.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;

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
     * Returns the HotelManagementSystem.
     *
     * @see seedu.hms.model.Model#getHotelManagementSystem()
     */
    ReadOnlyHotelManagementSystem getHotelManagementSystem();

    /**
     * Returns an unmodifiable view of the filtered list of customers
     */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Returns an unmodifiable view of the filtered list of bookings
     */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Returns an unmodifiable view of the filtered list of reservations
     */
    ObservableList<Reservation> getFilteredReservationList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' hms book file path.
     */
    Path getHotelManagementSystemFilePath();

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
     * @see seedu.hms.model.CustomerModel#selectedCustomerProperty()
     */
    ReadOnlyProperty<Customer> selectedCustomerProperty();

    /**
     * Selected bookings in the filtered booking list.
     * null if no booking is selected.
     *
     * @see seedu.hms.model.CustomerModel#selectedCustomerProperty()
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Sets the selected customer in the filtered customer list.
     *
     * @see seedu.hms.model.CustomerModel#setSelectedCustomer(Customer)
     */
    void setSelectedCustomer(Customer customer);

    /**
     * Sets the selected customer in the filtered customer list.
     *
     * @see BookingModel#setSelectedBooking(Booking)
     */
    void setSelectedBooking(Booking booking);

    /**
     * Sets the selected reservation in the filtered reservation list.
     *
     * @see ReservationModel#setSelectedReservation(Reservation)
     */
    void setSelectedReservation(Reservation reservation);
}
