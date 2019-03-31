package seedu.hms.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;

/**
 * The API of the Model component.
 */
public interface BillModel extends Model {


    /**
     * Returns an unmodifiable view of the filtered booking list
     */
    ObservableList<Booking> getFilteredBookingList();


    /**
     * Updates the filter of the filtered booking list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookingList(Predicate<Booking> predicate);


    /**
     * Generates booking bill of the specific customer identified
     */
    double generateBillForBooking(ObservableList<Booking> bookingObservableList);

    /**
     * Generates reservation bill of the specific customer identified
     */
    double generateBillForReservation(ObservableList<Reservation> reservationObservableList);

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
    
}