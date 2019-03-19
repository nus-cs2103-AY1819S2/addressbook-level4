package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;

/**
 * Wraps all statistics-related data
 */
public class Statistics implements ReadOnlyStatistics {

    private final DailyRevenueList dailyRevenueList;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dailyRevenueList = new DailyRevenueList();
    }

    /**
     * Creates a Statistics
     */
    public Statistics() {
    }

    /**
     * Creates a Statistics using the Bill in the {@code toBeCopied}
     */
    public Statistics(ReadOnlyStatistics toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the daily revenue list with {@code dailyRevenueList}.
     */
    public void setDailyRevenues(List<DailyRevenue> dailyRevenueList) {
        this.dailyRevenueList.setDailyRevenueList(dailyRevenueList);
        indicateModified();
    }

    /**
     * Resets the existing data of this RestOrRant's {@code statistics} with {@code newData}.
     */
    public void resetData(ReadOnlyStatistics newData) {
        requireNonNull(newData);

        setDailyRevenues(newData.getDailyRevenueList());
    }

    //// order item-level operations

    /**
     * Returns true if a daily revenue with the same identity as {@code daily Revenue} exists in the RestOrRant
     * Statistics.
     */
    public boolean hasDailyRevenue(DailyRevenue dailyRevenue) {
        requireNonNull(dailyRevenue);
        return dailyRevenueList.contains(dailyRevenue);
    }

    /**
     * Adds an daily revenue to the RestOrRant's statistics.
     * The daily revenue must not already exist in the Statistics.
     */
    public void addDailyRevenue(DailyRevenue dailyRevenue) {
        dailyRevenueList.add(dailyRevenue);
        indicateModified();
    }

    /**
     * Replaces the given daily revenue {@code target} in the list with {@code editedDailyRevenue}.
     * {@code target} must exist in the RestOrRant's statistics.
     * The daily revenue identity of {@code editedODailyRevenue} must not be the same as another existing daily revenue
     * in Statistics.
     */
    public void setDailyRevenue(DailyRevenue target, DailyRevenue editedDailyRevenue) {
        requireNonNull(editedDailyRevenue);

        dailyRevenueList.setDailyRevenue(target, editedDailyRevenue);
        indicateModified();
    }

    /**
     * Removes {@code dailyRevenue} from this RestOrRant's {@code DailyRevenueList}.
     * {@code dailyRevenue} must exist in the Statistics.
     */
    public void removeDailyRevenue(DailyRevenue dailyRevenue) {
        dailyRevenueList.remove(dailyRevenue);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the statistics has been modified.
     */
    public void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return dailyRevenueList.asUnmodifiableObservableList().size() + " daily revenue";
        // TODO: refine later
    }

    @Override
    public ObservableList<DailyRevenue> getDailyRevenueList() {
        return dailyRevenueList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && dailyRevenueList.equals(((Statistics) other).dailyRevenueList));
    }

    @Override
    public int hashCode() {
        return dailyRevenueList.hashCode();
    }
}
