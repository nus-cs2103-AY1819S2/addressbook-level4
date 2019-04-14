package seedu.hms.model.booking.serviceType;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.serviceType.exceptions.DuplicateServiceTypeException;
import seedu.hms.model.booking.serviceType.exceptions.ServiceTypeNotFoundException;

/**
 * A list of ServiceTypes
 * <p>
 * Supports a minimal set of list operations.
 */
public class ServiceTypeList implements Iterable<ServiceType> {

    private final ObservableList<ServiceType> internalList = FXCollections.observableArrayList();
    private final ObservableList<ServiceType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent customer as the given argument.
     */
    public boolean contains(ServiceType toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a ServiceType to the list.
     */
    public void add(ServiceType toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateServiceTypeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ServiceType {@code target} in the list with {@code editedServiceType}.
     * {@code target} must exist in the list.
     * The ServiceType identity of {@code editedServiceType} must not be the same as another existing ServiceType
     * in the list.
     */
    public void setServiceType(int serviceTypeIndex, ServiceType editedServiceType) {
        requireNonNull(editedServiceType);
        if (serviceTypeIndex < 0 || serviceTypeIndex > internalList.size()) {
            throw new ServiceTypeNotFoundException();
        }
        internalList.set(serviceTypeIndex, editedServiceType);
    }

    public void setServiceTypes(ServiceTypeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Bookings}.
     * {@code Bookings} must not contain duplicate Bookings.
     */
    public void setServiceTypes(List<ServiceType> serviceTypes) {
        requireAllNonNull(serviceTypes);
        internalList.setAll(serviceTypes);
    }

    /**
     * Removes the equivalent ServiceType from the list.
     * The ServiceType must exist in the list.
     */
    public void remove(int toRemove) {
        requireNonNull(toRemove);
        if (toRemove < 0 || toRemove > internalList.size()) {
            throw new ServiceTypeNotFoundException();
        }
        internalList.remove(toRemove);
    }

    /**
     * Removes the equivalent ServiceType from the list.
     * The ServiceType must exist in the list.
     */
    public void remove(ServiceType toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ServiceTypeNotFoundException();
        }
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
