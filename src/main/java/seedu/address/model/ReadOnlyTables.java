package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.table.Table;

public interface ReadOnlyTables extends Observable {

    /**
     * Returns an unmodifiable view of the tables list.
     */
    ObservableList<Table> getTableList();
}
