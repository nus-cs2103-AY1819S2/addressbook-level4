package seedu.hms.model;

import java.util.HashMap;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * The API of the Model component.
 */
public interface BillModel extends Model {


    /**
     * Returns an unmodifiable view of the filtered booking list
     */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Update the bill
     */
    void updateBill(Bill bill);

    /**
     * Get the bill
     */
    ReadOnlyProperty<Bill> getBill();

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
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    /**
     * Returns a HashMap that contains the entire booking bill for selected customer
     */
    HashMap<ServiceType, Pair<Double, Integer>> generateHashMapForBooking(
        ObservableList<Booking> bookingObservableList);

    /**
     * Returns a HashMap that contains the entire reservation bill for selected customer
     */
    HashMap<RoomType, Pair<Double, Long>> generateHashMapForReservation(ObservableList<Reservation>
                                                                          reservationObservableList);

}
