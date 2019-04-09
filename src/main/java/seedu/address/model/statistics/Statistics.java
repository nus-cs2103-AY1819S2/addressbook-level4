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

    private final RevenueList revenueList;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        revenueList = new RevenueList();
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
     * Replaces the contents of the revenue list with {@code revenueList}.
     */
    public void setRevenues(List<Revenue> revenueList) {
        this.revenueList.setRevenueList(revenueList);
        indicateModified();
    }

    /**
     * Resets the existing data of this RestOrRant's {@code statistics} with {@code newData}.
     */
    public void resetData(ReadOnlyStatistics newData) {
        requireNonNull(newData);

        setRevenues(newData.getRevenueList());
    }

    //// order item-level operations

    /**
     * Returns true if a revenue with the same identity as {@code Revenue} exists in the RestOrRant
     * Statistics.
     */
    public boolean hasRevenue(Revenue revenue) {
        requireNonNull(revenue);
        return revenueList.contains(revenue);
    }

    /**
     * Adds an revenue to the RestOrRant's statistics.
     * The revenue must not already exist in the Statistics.
     */
    public void addRevenue(Revenue revenue) {
        revenueList.add(revenue);
        indicateModified();
    }

    /**
     * Replaces the given revenue {@code target} in the list with {@code editedRevenue}.
     * {@code target} must exist in the RestOrRant's statistics.
     * The revenue identity of {@code editedRevenue} must not be the same as another existing revenue in Statistics.
     */
    public void setRevenue(Revenue target, Revenue editedRevenue) {
        requireNonNull(editedRevenue);

        revenueList.setRevenue(target, editedRevenue);
        indicateModified();
    }

    /**
     * Removes {@code revenue} from this RestOrRant's {@code RevenueList}.
     * {@code dailyRevenue} must exist in the Statistics.
     */
    public void removeRevenue(Revenue revenue) {
        revenueList.remove(revenue);
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
        return revenueList.asUnmodifiableObservableList().size() + " revenue";
    }

    @Override
    public ObservableList<Revenue> getRevenueList() {
        return revenueList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && revenueList.equals(((Statistics) other).revenueList));
    }

    @Override
    public int hashCode() {
        return revenueList.hashCode();
    }
}
