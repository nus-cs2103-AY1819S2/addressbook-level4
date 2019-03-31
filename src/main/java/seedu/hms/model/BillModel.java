package seedu.hms.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;

/**
 * The API of the Model component.
 */
public interface BillModel extends Model {

    /**
     * Selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    ReadOnlyProperty<Booking> selectedBookingProperty();

    /**
     * Returns the selected booking in the filtered booking list.
     * null if no booking is selected.
     */
    Booking getSelectedBooking();

    /**
     * Sets the selected booking in the filtered booking list.
     */
    void setSelectedBooking(Booking booking);

    /**
     * Returns an unmodifiable view of the filtered booking list
     */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Returns an unmodifiable view of the serviceType list
     */
    ObservableList<ServiceType> getServiceTypeList();

    /**
     * Updates the filter of the filtered booking list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookingList(Predicate<Booking> predicate);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteBooking(int target);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteBooking(Booking b);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the hms book.
     */
    void addBooking(Booking booking);

    /**
     * Replaces the given booking {@code target} with {@code editedBooking}.
     * {@code target} must exist in the hms book.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the hms
     * book.
     */
    void setBooking(int target, Booking editedBooking);

    /**
     * Selected serviceType in the serviceType list.
     * null if no service type is selected.
     */
    ReadOnlyProperty<ServiceType> selectedServiceTypeProperty();

    /**
     * Returns the selected serviceType in the serviceType list.
     * null if no serviceType is selected.
     */
    ServiceType getSelectedServiceType();

    /**
     * Sets the selected serviceType in the serviceType list.
     */
    void setSelectedServiceType(ServiceType serviceType);

    /**
     * Clears all the bookings present in the {@code hotelManagementSystem}.
     */
    void setClearBooking(ReadOnlyHotelManagementSystem hotelManagementSystem);

    /**
     * Generates booking bill of the specific customer identified
     */
    double generateBillForBooking(ObservableList<Booking> bookingObservableList);

    /**
     * Generates reservation bill of the specific customer identified
     */
    double generateBillForReservation(ObservableList<Reservation> reservationObservableList);

    /**
     * Selected reservation in the filtered reservation list.
     * null if no reservation is selected.
     */
    ReadOnlyProperty<Reservation> selectedReservationProperty();

    /**
     * Returns the selected reservation in the filtered reservation list.
     * null if no reservation is selected.
     */
    Reservation getSelectedReservation();

    /**
     * Sets the selected reservation in the filtered reservation list.
     */
    void setSelectedReservation(Reservation reservation);

    /**
     * Returns an unmodifiable view of the filtered reservation list
     */
    ObservableList<Reservation> getFilteredReservationList();

    /**
     * Returns an unmodifiable view of the filtered reservation list
     */
    ObservableList<RoomType> getRoomTypeList();

    /**
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    /**
     * Deletes the given reservation.
     * The reservation must exist in the hms book.
     */
    void deleteReservation(int target);

    /**
     * Adds the given reservation.
     * {@code reservation} must not already exist in the hms book.
     */
    void addReservation(Reservation reservation);

    /**
     * Selected roomType in the roomType list.
     * null if no roomType is selected.
     */
    ReadOnlyProperty<RoomType> selectedRoomTypeProperty();


    /**
     * Returns the selected roomType in the roomType list.
     * null if no roomType is selected.
     */
    RoomType getSelectedRoomType();

    /**
     * Sets the selected serviceType in the serviceType list.
     */
    void setSelectedRoomType(RoomType roomType);

    /**
     * Replaces the given reservation {@code target} with {@code editedReservation}.
     * {@code target} must exist in the hms book.
     * The reservation identity of {@code editedReservation} must not be the same as another existing reservation in
     * the hms
     * book.
     */
    void setReservation(int target, Reservation editedReservation);


    /**
     * Clears all the reservations present in the {@code hotelManagementSystem}.
     */
    void setClearReservation(ReadOnlyHotelManagementSystem hotelManagementSystem);

}
