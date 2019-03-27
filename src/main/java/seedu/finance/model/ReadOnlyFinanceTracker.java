package seedu.finance.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.finance.model.record.Amount;
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

    ObjectProperty<Amount> getBudget();

}
