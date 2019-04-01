package seedu.finance.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.record.Record;

/**
 * Unmodifiable view of an finance tracker
 */
public interface ReadOnlyFinanceTracker extends Observable {

    /**
     * Returns an unmodifiable view of the records list.
     * This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();

    Budget getBudget();

}
