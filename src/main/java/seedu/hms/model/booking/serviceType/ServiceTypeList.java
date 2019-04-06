package seedu.hms.model.booking.serviceType;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.booking.serviceType.exceptions.ServiceTypeNotFoundException;
import seedu.hms.model.util.TimeRange;

/**
 * A list of ServiceTypes
 * <p>
 * Supports a minimal set of list operations.
 */
public class ServiceTypeList implements Iterable<ServiceType> {

    private final ObservableList<ServiceType> internalList = FXCollections.observableArrayList();
    private final ObservableList<ServiceType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    public ServiceTypeList() {
        this.setServiceTypes();
    }

    public void setServiceTypes() {
        internalList.add(new ServiceType(50, new TimeRange(8, 22), "Gym", 7.0));
        internalList.add(new ServiceType(30, new TimeRange(10, 20), "Spa", 10.0));
        internalList.add(new ServiceType(40, new TimeRange(12, 18), "Pool", 8.0));
        internalList.add(new ServiceType(20, new TimeRange(10, 22), "Games Room", 5.0));
    }

    /**
     * Adds a ServiceType to the list.
     */
    public void add(ServiceType toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the ServiceType {@code target} in the list with {@code editedServiceType}.
     * {@code target} must exist in the list.
     * The ServiceType identity of {@code editedServiceType} must not be the same as another existing ServiceType in the list.
     */
    public void setServiceType(int serviceTypeIndex, ServiceType editedServiceType) {
        requireNonNull(editedServiceType);
        if (serviceTypeIndex < 0 || serviceTypeIndex > internalList.size()) {
            throw new ServiceTypeNotFoundException();
        }
        internalList.set(serviceTypeIndex, editedServiceType);
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
