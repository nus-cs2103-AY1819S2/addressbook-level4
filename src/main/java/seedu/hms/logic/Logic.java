package seedu.hms.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillModel;
import seedu.hms.model.BookingModel;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

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
     * Returns an unmodifiable view of the filtered list of serviceTypes
     */
    ObservableList<ServiceType> getServiceTypeList();

    /**
     * Returns an unmodifiable view of the filtered list of reservations
     */
    ObservableList<Reservation> getFilteredReservationList();

    /**
     * Returns an unmodifiable view of the filtered list of roomTypes
     */
    ObservableList<RoomType> getRoomTypeList();

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
     * @see seedu.hms.model.BookingModel#selectedBookingProperty()
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Selected bookings in the filtered booking list.
     * null if no booking is selected.
     *
     * @see seedu.hms.model.BookingModel#selectedBookingProperty()
     */
    ReadOnlyProperty<Reservation> selectedReservationProperty();

    /**
     * Selected serviceType in the serviceType list.
     * null if no serviceType is selected.
     *
     * @see seedu.hms.model.BookingModel#selectedServiceTypeProperty()
     */
    ReadOnlyProperty<ServiceType> selectedServiceTypeProperty();

    /**
     * Selected roomType in the roomType list.
     * null if no roomType is selected.
     *
     * @see seedu.hms.model.ReservationModel#selectedRoomTypeProperty()
     */
    ReadOnlyProperty<RoomType> selectedRoomTypeProperty();

    /**
     * Sets the selected customer in the filtered customer list.
     *
     * @see seedu.hms.model.CustomerModel#setSelectedCustomer(Customer)
     */
    void setSelectedCustomer(Customer customer);


    /**
     * Sets the selected serviceType in the filtered serviceType list.
     *
     * @see seedu.hms.model.BookingModel#setSelectedServiceType(ServiceType)
     */
    void setSelectedServiceType(ServiceType serviceType);

    /**
     * Sets the selected booking in the filtered booking list.
     *
     * @see BookingModel#setSelectedBooking(Booking)
     */
    void setSelectedBooking(Booking booking);

    /**
     * Sets the selected serviceType in the filtered serviceType list.
     *
     * @see seedu.hms.model.ReservationModel#setSelectedRoomType(RoomType)
     */
    void setSelectedRoomType (RoomType roomType);

    /**
     * Sets the selected reservation in the filtered reservation list.
     *
     * @see ReservationModel#setSelectedReservation(Reservation)
     */
    void setSelectedReservation(Reservation reservation);

    BillModel getBillModel();
}
