package seedu.hms.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.model.reservation.Reservation;

/**
 * The API of the Model component.
 */
public interface ReservationModel extends Model {

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
