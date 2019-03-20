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
public class ServiceTypeList implements Iterable<ServiceType> {

    private final ObservableList<ServiceType> internalList = FXCollections.observableArrayList();
    private final ObservableList<ServiceType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void setBookings(ServiceTypeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ServiceType> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ServiceTypeList // instanceof handles nulls
                && internalList.equals(((ServiceTypeList) other).internalList));
    }

    @Override
    public Iterator<ServiceType> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
