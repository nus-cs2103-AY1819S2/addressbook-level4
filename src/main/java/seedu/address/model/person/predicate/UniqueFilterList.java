package seedu.address.model.person.predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateFilterException;
import seedu.address.model.person.exceptions.FilterNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Filter#isSameFilter(Filter)
 */
public class UniqueFilterList implements Iterable<Filter> {

    private final ObservableList<Filter> internalList = FXCollections.observableArrayList();
    private final ObservableList<Filter> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Filter toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFilter);
    }

    public Filter getFilter(String predicateName) {
        requireAllNonNull(predicateName);
        Filter predicateManager = new Filter(predicateName, null);
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSameFilter(predicateManager)) {
                return internalList.get(i);
            }
        }
        return null;
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Filter toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFilterException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Filter toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FilterNotFoundException();
        }
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Filter> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Filter> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueFilterList // instanceof handles nulls
            && internalList.equals(((UniqueFilterList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
