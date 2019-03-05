package seedu.address.model.table;

import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.table.Table;

public interface ReadOnlyTables extends Observable {

    /**
     * Returns an unmodifiable view of the tables list.
     */
    ObservableList<Table> getTableList();
    
    Optional<Table> getTableFromNumber(TableNumber tableNumber);
}
