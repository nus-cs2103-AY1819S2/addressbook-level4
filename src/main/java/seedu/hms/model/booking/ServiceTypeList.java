package seedu.hms.model.booking;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of ServiceTypes
 * <p>
 * Supports a minimal set of list operations.
 */
public class ServiceTypeList implements Iterable<ServiceType> {

    private final ObservableList<ServiceType> internalList = FXCollections.observableArrayList();
    private final ObservableList<ServiceType> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void setServiceTypes() {
        internalList.setAll(ServiceType.values());
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
