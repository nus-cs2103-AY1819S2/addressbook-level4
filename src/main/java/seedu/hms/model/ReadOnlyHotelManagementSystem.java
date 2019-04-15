package seedu.hms.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Unmodifiable view of an hms book
 */
public interface ReadOnlyHotelManagementSystem extends Observable {

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();

    /**
     * Returns an unmodifiable view of the bookings list.
     */
    ObservableList<Booking> getBookingList();

    /**
     * Returns an unmodifiable view of the serviceType list.
     * @return
     */
    ObservableList<ServiceType> getServiceTypeList();

    /**
     * Returns an unmodifiable view of the reservations list.
     */
    ObservableList<Reservation> getReservationList();

    /**
     * Returns an unmodifiable view of the serviceType list.
     */
    ObservableList<RoomType> getRoomTypeList();
}
