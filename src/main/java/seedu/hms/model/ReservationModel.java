package seedu.hms.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * The API of the Model component.
 */
public interface ReservationModel extends Model {

    /**
     * Selected reservation in the filtered reservation list.
     * null if no reservation is selected.
     */
    ReadOnlyProperty<Reservation> selectedReservationProperty();

    boolean hasRoomType(RoomType st);


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
     * Returns an unmodifiable view of the roomType list
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
     * Deletes the given reservation.
     * The reservation must exist in the hms book.
     */
    void deleteReservation(Reservation r);

    /**
     * Adds the given reservation.
     * {@code reservation} must not already exist in the hms book.
     */
    void addReservation(Reservation reservation);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteRoomType(int target);

    /**
     * Deletes the given booking.
     * The booking must exist in the hms book.
     */
    void deleteRoomType(RoomType st);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the hms book.
     */
    void addRoomType(RoomType roomType);

    /**
     * Replaces the given booking {@code target} with {@code editedBooking}.
     * {@code target} must exist in the hms book.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the hms
     * book.
     */
    void setRoomType(int target, RoomType editedRoomType);

    RoomType getRoomType(String roomName);

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
     * Sets the selected roomType in the roomType list.
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


    /**
     * Clears all the room types present in the {@code hotelManagementSystem}.
     */
    void setClearRoomTypes(ReadOnlyHotelManagementSystem hotelManagementSystem);
}
