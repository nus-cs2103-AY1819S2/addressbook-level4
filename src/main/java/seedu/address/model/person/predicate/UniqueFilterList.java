package seedu.address.model.person.predicate;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateFilterException;
import seedu.address.model.person.exceptions.FilterNotFoundException;

/**
 * A list of filters that enforces uniqueness between its elements and does not allow nulls.
 * A filter is considered unique by comparing using {@code Filter#isSameFilter(Filter)}. As such, adding and updating of
 * filters uses  Filter#isSameFilter(Filter) for equality so as to ensure that the filter being added or updated is
 * unique in terms of identity in the UniqueFilterList. However, the removal of a filter uses Filter#equals(Object) so
 * as to ensure that the filter with exactly the same fields will be removed.
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
