package seedu.address.model.statistics;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of statistics
 */
public interface ReadOnlyStatistics extends Observable {

    /**
     * Returns an unmodifiable view of the Revenue list for the statistics.
     */
    ObservableList<Revenue> getRevenueList();
}
