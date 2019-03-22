package seedu.address.model.person.healthworker;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * @author Lookaz
 *
 * A list of HealthWorker objects that enforces uniqueness between its elements
 * and does not allow nulls. A person is considered unique by comparing using
 * {@code HealthWorker#isSameHealthWorker(HealthWorker)}. As such, adding and
 * updating of HealthWorkers uses HealthWorker#isSameHealthWorker(HealthWorker)
 * for equality so as to ensure that the HealthWorker being added or updated is
 * unique in terms of identity in the UniqueHealthWorkerList. However, the
 * removal of a person uses HealthWorker#equals(HealthWorker) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see HealthWorker#isSameHealthWorker(HealthWorker)
 */
public class UniqueHealthWorkerList implements Iterable<HealthWorker> {

    private final ObservableList<HealthWorker> internalList = FXCollections
            .observableArrayList();
    private final ObservableList<HealthWorker> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent HealthWorker as the
     * given argument.
     */
    public boolean contains(HealthWorker toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSameHealthWorker);
    }

    /**
     * Adds a HealthWorker to the list.
     * The person must not already exist in the list.
     */
    public void add(HealthWorker toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the HealthWorker {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The person identity of {@code edited} must not be the same as another
     * existing HealthWorker in the list.
     */
    public void setHealthWorker(HealthWorker target, HealthWorker edited) {
        requireAllNonNull(target, edited);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameHealthWorker(edited) && contains(edited)) {
            throw new DuplicatePersonException();
        }

        this.internalList.set(index, edited);
    }

    /**
     * Removes the equivalent HealthWorker from the list.
     * The HealthWorker must exist in the list.
     */
    public void remove(HealthWorker toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setHealthWorkers(UniqueHealthWorkerList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code healthWorkers}.
     * {@code healthWorkers} must not contain duplicate persons.
     */
    public void setHealthWorkers(List<HealthWorker> healthWorkers) {
        requireAllNonNull(healthWorkers);
        if (!workersAreUnique(healthWorkers)) {
            throw new DuplicatePersonException();
        }

        this.internalList.setAll(healthWorkers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<HealthWorker> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<HealthWorker> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueHealthWorkerList // instanceof handles nulls
                && this.internalList.equals(((UniqueHealthWorkerList) other).internalList));
    }

    /**
     * Returns true if {@code persons} contains only unique HealthWorkers.
     */
    private boolean workersAreUnique(List<HealthWorker> workers) {
        for (int i = 0; i < workers.size() - 1; i++) {
            for (int j = i + 1; j < workers.size(); j++) {
                if (workers.get(i).isSameHealthWorker(workers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public HealthWorker getAt(int index) {
        assert(index < this.internalList.size() - 1);
        return this.internalList.get(index);
    }

}
