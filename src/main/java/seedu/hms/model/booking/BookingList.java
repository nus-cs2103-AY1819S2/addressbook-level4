package seedu.hms.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.exceptions.BookingNotFoundException;
import seedu.hms.model.booking.exceptions.ServiceFullException;
import seedu.hms.model.booking.exceptions.ServiceUnavailableException;
import seedu.hms.model.util.TimeRange;

/**
 * A list of Bookings that enforces all elements are non-null and that none of the bookings exceed the service's
 * capacity within the service's available hours.
 * <p>
 * Supports a minimal set of list operations.
 */
public class BookingList implements Iterable<Booking> {

    private final ObservableList<Booking> internalList = FXCollections.observableArrayList();
    private final ObservableList<Booking> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks if booking is within the service's operational hours
     */
    private boolean duringOperation(Booking booking) {
        return booking.getTiming().withinTiming(booking.getService().getTiming());
    }

    private List<Booking> getOverlappingBookings(TimeRange t) {
        List<Booking> overlappingBookings = new ArrayList<Booking>();
        for (Booking b : internalList) {
            if (t.withinTiming(b.getTiming())) {
                overlappingBookings.add(b);
            }
        }
        return overlappingBookings;
    }

    /**
     * Checks if service is full during requested hours
     */
    private Optional<TimeRange> isServiceFull(Booking booking) {
        for (TimeRange bookingHour : booking.getTiming().getHourlySlots()) {
            if (getOverlappingBookings(bookingHour).stream().mapToInt(Booking::numOfCustomers).sum()
                > booking.getService().getCapacity()) {
                return Optional.of(bookingHour);
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a Booking to the list.
     */
    public void add(Booking toAdd) {
        requireNonNull(toAdd);
        if (!duringOperation(toAdd)) {
            throw new ServiceUnavailableException();
        }
        Optional<TimeRange> overlapping = isServiceFull(toAdd);
        if (overlapping.isPresent()) {
            throw new ServiceFullException(overlapping.get());
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Booking {@code target} in the list with {@code editedBooking}.
     * {@code target} must exist in the list.
     * The Booking identity of {@code editedBooking} must not be the same as another existing Booking in the list.
     */
    public void setBooking(int bookingIndex, Booking editedBooking) {
        requireNonNull(editedBooking);

        if (bookingIndex < 0 || bookingIndex > internalList.size()) {
            throw new BookingNotFoundException();
        }
        if (!duringOperation(editedBooking)) {
            throw new ServiceUnavailableException();
        }
        Optional<TimeRange> overlapping = isServiceFull(editedBooking);
        if (overlapping.isPresent()) {
            throw new ServiceFullException(overlapping.get());
        }

        internalList.set(bookingIndex, editedBooking);
    }

    /**
     * Removes the equivalent Booking from the list.
     * The Booking must exist in the list.
     */
    public void remove(int toRemove) {
        requireNonNull(toRemove);
        if (toRemove < 0 || toRemove > internalList.size()) {
            throw new BookingNotFoundException();
        }
        internalList.remove(toRemove);
    }

    /**
     * Removes the equivalent booking from the list.
     * The customer must exist in the list.
     */
    public void remove(Booking toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookingNotFoundException();
        }
    }

    public void setBookings(BookingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Bookings}.
     * {@code Bookings} must not contain duplicate Bookings.
     */
    public void setBookings(List<Booking> bookings) {
        requireAllNonNull(bookings);
        internalList.setAll(bookings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Booking> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BookingList)) {
            return false;
        }
        BookingList ob = (BookingList) other;
        boolean eq = true;
        for (int i = 0; i < internalList.size(); i++) {
            if (!internalList.get(i).equals(ob.internalList.get(i))) {
                eq = false;
            }
        }
        return eq;
    }

    @Override
    public Iterator<Booking> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
