package seedu.address.model.person.predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePredicateManagerException;
import seedu.address.model.person.exceptions.PredicateManagerNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see PredicateManager#isSamePredicateManager(PredicateManager)
 */
public class UniquePredicateList implements Iterable<PredicateManager> {

    private final ObservableList<PredicateManager> internalList = FXCollections.observableArrayList();
    private final ObservableList<PredicateManager> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(PredicateManager toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePredicateManager);
    }

    public PredicateManager getPredicateManager(String predicateName) {
        requireAllNonNull(predicateName);
        PredicateManager predicateManager = new PredicateManager(predicateName);
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSamePredicateManager(predicateManager)) {
                return internalList.get(i);
            }
        }
        return null;
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(PredicateManager toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePredicateManagerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(PredicateManager toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PredicateManagerNotFoundException();
        }
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PredicateManager> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<PredicateManager> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePredicateList // instanceof handles nulls
                        && internalList.equals(((UniquePredicateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
