package seedu.finance.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.finance.model.record.Record;

/**
 * Unmodifiable view of an finance book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Record> getRecordList();

}
