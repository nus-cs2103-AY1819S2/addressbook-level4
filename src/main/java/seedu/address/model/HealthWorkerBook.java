package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;

import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.UniqueHealthWorkerList;

/**
 * @author Lookaz
 * Wrapper class for storing HealthWorkers and operations involving HealthWorker objects.
 * Duplicates are not allowed (by .isSameHealthWorker comparison)
 */
public class HealthWorkerBook implements ReadOnlyHealthWorkerBook {

    private final UniqueHealthWorkerList uniqueHealthWorkerList;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    {
        this.uniqueHealthWorkerList = new UniqueHealthWorkerList();
    }

    public HealthWorkerBook() {}

    public HealthWorkerBook(ReadOnlyHealthWorkerBook toCopy) {
        this();
        resetData(toCopy);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setHealthWorkers(List<HealthWorker> healthWorkers) {
        this.uniqueHealthWorkerList.setHealthWorkers(healthWorkers);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code HealthWorkerBook} with {@code newData}.
     */
    public void resetData(ReadOnlyHealthWorkerBook newData) {
        requireNonNull(newData);

        setHealthWorkers(newData.getHealthWorkerList());
    }

    /**
     * Returns true if a HealthWorker with the same identity as {@code worker}
     * exists in the HealthWorkerBook.
     */
    public boolean hasHealthWorker(HealthWorker healthWorker) {
        requireNonNull(healthWorker);
        return this.uniqueHealthWorkerList.contains(healthWorker);
    }

    /**
     * Adds a HealthWorker to the address book.
     * HealthWorker object must not already exist in the HealthWorkerBook.
     */
    public void addHealthWorker(HealthWorker healthWorker) {
        this.uniqueHealthWorkerList.add(healthWorker);
        indicateModified();
    }

    /**
     * Replaces the given HealthWorker {@code target} in the list with {@code editedWorker}.
     * {@code target} must exist in the HealthWorkerBook.
     * The person identity of {@code editedWorker} must not be the same as another
     * existing HealthWorker in the HealthWorkerBook.
     */
    public void setHealthWorker(HealthWorker target, HealthWorker editedWorker) {
        requireNonNull(editedWorker);
        this.uniqueHealthWorkerList.setHealthWorker(target, editedWorker);

        indicateModified();
    }

    /**
     * Removes {@code worker} from this {@code HealthWorkerBook}.
     * {@code worker} must exist in the HealthWorkerBook.
     */
    public void removeHealthWorker(HealthWorker worker) {
        this.uniqueHealthWorkerList.remove(worker);
        indicateModified();
    }

    /**
     * Notifies listeners that HealthWorkerBook has been modified.
     */
    protected void indicateModified() {
        this.invalidationListenerManager.callListeners(this);
    }

    @Override
    public ObservableList<HealthWorker> getHealthWorkerList() {
        return this.uniqueHealthWorkerList.asUnmodifiableObservableList();
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        this.invalidationListenerManager.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        this.invalidationListenerManager.removeListener(invalidationListener);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        return other instanceof HealthWorkerBook && this.uniqueHealthWorkerList
                .equals(((HealthWorkerBook) other).uniqueHealthWorkerList);
    }

    @Override
    public int hashCode() {
        return this.uniqueHealthWorkerList.hashCode();
    }


}
