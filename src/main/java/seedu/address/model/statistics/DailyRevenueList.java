package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.statistics.exception.DailyRevenueNotFoundException;

/**
 * A list of daily revenues.
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
     */
    public void add(DailyRevenue toAdd) {
        requireNonNull(toAdd);
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

        internalList.set(index, editedDailyRevenue);
    }

    /**
     * Replaces the Observable<DailyRevenueList> {@code internalList} with another DailyRevenueList {@code replacement}.
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
     */
    public void setDailyRevenueList(List<DailyRevenue> dailyRevenueList) {
        requireAllNonNull(dailyRevenueList);
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

}
