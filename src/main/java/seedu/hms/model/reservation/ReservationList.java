package seedu.hms.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.reservation.exceptions.ReservationNotFoundException;
import seedu.hms.model.reservation.exceptions.RoomFullException;
import seedu.hms.model.reservation.exceptions.RoomUnavailableException;
import seedu.hms.model.util.DateRange;

/**
 * A list of Reservations that enforces all elements are non-null and that none of the reservations exceed the room's
 * capacity within the room's available dates.
 * <p>
 * Supports a minimal set of list operations.
 */
public class ReservationList implements Iterable<Reservation> {

    private final ObservableList<Reservation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);
    

    private List<Reservation> getOverlappingReservations(DateRange t) {
        List<Reservation> overlappingReservations = new ArrayList<Reservation>();
        for (Reservation b : internalList) {
            if (t.withinDates(b.getDates())) {
                overlappingReservations.add(b);
            }
        }
        return overlappingReservations;
    }

    /**
     * Checks if room is full during requested dates
     */
    private Optional<DateRange> isRoomFull(Reservation reservation) {
        for (DateRange reservationDate : reservation.getDates().getEachDay()) {
            if (getOverlappingReservations(reservationDate).size() > reservation.getRoom().getNumberOfRooms()) {
                return Optional.of(reservationDate);
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a Reservation to the list.
     */
    public void add(Reservation toAdd) {
        requireNonNull(toAdd);
        Optional<DateRange> overlapping = isRoomFull(toAdd);
        if (overlapping.isPresent()) {
            throw new RoomFullException(overlapping.get());
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the list.
     * The Reservation identity of {@code editedReservation} must not be the same as another existing Reservation in the list.
     */
    public void setReservation(int reservationIndex, Reservation editedReservation) {
        requireNonNull(editedReservation);

        if (reservationIndex < 0 || reservationIndex > internalList.size()) {
            throw new ReservationNotFoundException();
        }
        Optional<DateRange> overlapping = isRoomFull(editedReservation);
        if (overlapping.isPresent()) {
            throw new RoomFullException(overlapping.get());
        }

        internalList.set(reservationIndex, editedReservation);
    }

    /**
     * Removes the equivalent Reservation from the list.
     * The Reservation must exist in the list.
     */
    public void remove(int toRemove) {
        requireNonNull(toRemove);
        if (toRemove < 0 || toRemove > internalList.size()) {
            throw new ReservationNotFoundException();
        }
        internalList.remove(toRemove);
    }

    public void setReservations(ReservationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Reservations}.
     * {@code Reservations} must not contain duplicate Reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        requireAllNonNull(reservations);
        internalList.setAll(reservations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reservation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReservationList // instanceof handles nulls
            && internalList.equals(((ReservationList) other).internalList));
    }

    @Override
    public Iterator<Reservation> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
