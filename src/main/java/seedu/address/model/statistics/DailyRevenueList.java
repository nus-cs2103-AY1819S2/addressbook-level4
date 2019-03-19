package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateOrderItemException;
import seedu.address.model.statistics.exceptions.DailyRevenueNotFoundException;
import seedu.address.model.statistics.exceptions.DuplicateDailyRevenueException;

/**
 * A list of daily revenues that enforces uniqueness between its elements and does not allow nulls.
 * A daily revenue is considered unique by comparing using {@code DailyRevenue#isSameDailyRevenue(DailyRevenue)}.
 * As such, adding and updating of daily revenue uses DailyRevenue#isSameDailyRevenue(DailyRevenue) for equality so
 * as to ensure that the daily revenue being added or updated is unique in terms of identity in the DailyRevenueList.
 * However, the removal of a daily revenue uses DailyRevenue#equals so as to ensure that the daily revenue with exactly
 * the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 */
public class DailyRevenueList implements Iterable<DailyRevenue> {

    private final ObservableList<DailyRevenue> internalList = FXCollections.observableArrayList();
    private final ObservableList<DailyRevenue> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent DailyRevenue as the given argument.
     */
    public boolean contains(DailyRevenue toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDailyRevenue);
    }
    /**
     * Adds a DailyRevenue for the day to the list.
     * The daily revenue must not already exist in the list.
     */
    public void add(DailyRevenue toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDailyRevenueException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the revenue {@code target} in the list with {@code editedDailyRevenue}.
     * {@code target} must exist in the list.
     * The order item identity of {@code editedDailyRevenue} must not be the same as another existing order item
     * in the list.
     */
    public void setDailyRevenue(DailyRevenue target, DailyRevenue editedDailyRevenue) {
        requireAllNonNull(target, editedDailyRevenue);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DailyRevenueNotFoundException();
        }

        if (!target.isSameDailyRevenue(editedDailyRevenue) && contains(editedDailyRevenue)) {
            throw new DuplicateOrderItemException();
        }

        internalList.set(index, editedDailyRevenue);
    }

    /**
     * Replaces the Observable<'DailyRevenue'> {@code internalList} with another DailyRevenueList {@code replacement}.
     */
    public void setDailyRevenue(DailyRevenueList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent daily revenue from the list.
     * The daily revenue must exist in the list.
     */
    public void remove(DailyRevenue toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DailyRevenueNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code dailyRevenueList}.
     * {@code dailyRevenueList} must not contain duplicate daily revenue.
     */
    public void setDailyRevenueList(List<DailyRevenue> dailyRevenueList) {
        requireAllNonNull(dailyRevenueList);
        if (!dailyRevenuesAreUnique(dailyRevenueList)) {
            throw new DuplicateOrderItemException();
        }
        internalList.setAll(dailyRevenueList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<DailyRevenue> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<DailyRevenue> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DailyRevenueList // instanceof handles nulls
                && internalList.equals(((DailyRevenueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


    /**
     * Returns true if {@code orderItems} contains only unique order items.
     */
    private boolean dailyRevenuesAreUnique(List<DailyRevenue> dailyRevenues) {
        for (int i = 0; i < dailyRevenues.size() - 1; i++) {
            for (int j = i + 1; j < dailyRevenues.size(); j++) {
                if (dailyRevenues.get(i).isSameDailyRevenue(dailyRevenues.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
