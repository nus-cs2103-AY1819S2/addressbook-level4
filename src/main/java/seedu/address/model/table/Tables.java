package seedu.address.model.table;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.ReadOnlyTables;

public class Tables implements ReadOnlyTables {

    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    private final UniqueTableList tableList;

    {
        tableList = new UniqueTableList(); 
    }
    
    public Tables() {}

    /**
     * Creates a Tables using the tableList in the {@code toBeCopied}
     */
    public Tables(ReadOnlyTables toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code tableList}.
     * {@code tableList} must not contain duplicate persons.
     */
    public void setTables(List<Table> tableList) {
        this.tableList.setTables(tableList);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code Tables} with {@code newData}.
     */
    public void resetData(ReadOnlyTables newData) {
        requireNonNull(newData);

        setTables(newData.getTableList());
    }

    //// person-level operations

    /**
     * Returns true if a table with the same identity as {@code table} exists in the UniqueTableList.
     */
    public boolean hasTable(Table table) {
        requireNonNull(table);
        return tableList.contains(table);
    }

    /**
     * Adds a table to the UniqueTableList.
     * The table must not already exist in the UniqueTableList.
     */
    public void addTable(Table t) {
        tableList.add(t);
        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setTable(Table target, Table editedTable) {
        requireNonNull(editedTable);

        tableList.setTable(target, editedTable);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestOrRant}.
     * {@code key} must exist in the address book.
     */
    public void removeTable(Table key) {
        tableList.remove(key);
        indicateModified();
    }

    /**
     * Notifies listeners that the RestOrRant has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }
    
    @Override
    public ObservableList<Table> getTableList() {
        return null;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tables // instanceof handles nulls
                && tableList.equals(((Tables) other).tableList));
    }
}
