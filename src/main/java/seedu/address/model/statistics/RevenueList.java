package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.statistics.exceptions.DuplicateRevenueException;
import seedu.address.model.statistics.exceptions.RevenueNotFoundException;

/**
 * A list of revenues that enforces uniqueness between its elements and does not allow nulls.
 * A revenue is considered unique by comparing using {@code Revenue#isSameRevenue(Revenue)}.
 * As such, adding and updating of revenue uses Revenue#isSameRevenue(Revenue) for equality so
 * as to ensure that the revenue being added or updated is unique in terms of identity in the RevenueList.
 * However, the removal of a revenue uses Revenue#equals so as to ensure that the revenue with exactly
 * the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 */
public class RevenueList implements Iterable<Revenue> {

    private final ObservableList<Revenue> internalList = FXCollections.observableArrayList();
    private final ObservableList<Revenue> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Revenue as the given argument.
     */
    public boolean contains(Revenue toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRevenue);
    }
    /**
     * Adds a Revenue for the day to the list.
     * The revenue must not already exist in the list.
     */
    public void add(Revenue toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRevenueException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the revenue {@code target} in the list with {@code editedRevenue}.
     * {@code target} must exist in the list.
     * The order item identity of {@code editedRevenue} must not be the same as another existing order item in the list.
     */
    public void setRevenue(Revenue target, Revenue editedRevenue) {
        requireAllNonNull(target, editedRevenue);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RevenueNotFoundException();
        }

        if (!target.isSameRevenue(editedRevenue) && contains(editedRevenue)) {
            throw new DuplicateRevenueException();
        }

        internalList.set(index, editedRevenue);
    }

    /**
     * Replaces the Observable<'Revenue'> {@code internalList} with another RevenueList {@code replacement}.
     */
    public void setRevenue(RevenueList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent revenue from the list.
     * The revenue must exist in the list.
     */
    public void remove(Revenue toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RevenueNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code revenueList}.
     * {@code revenueList} must not contain duplicate revenue.
     */
    public void setRevenueList(List<Revenue> revenueList) {
        requireAllNonNull(revenueList);
        if (!revenuesAreUnique(revenueList)) {
            throw new DuplicateRevenueException();
        }
        internalList.setAll(revenueList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Revenue> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Revenue> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RevenueList // instanceof handles nulls
                && internalList.equals(((RevenueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    /**
     * Returns true if {@code revenues} contains only unique revenues.
     */
    private boolean revenuesAreUnique(List<Revenue> revenues) {
        for (int i = 0; i < revenues.size() - 1; i++) {
            for (int j = i + 1; j < revenues.size(); j++) {
                if (revenues.get(i).isSameRevenue(revenues.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
