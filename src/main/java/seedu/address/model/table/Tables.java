package seedu.address.model.table;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Wraps all table-related data.
 * Duplicates are not allowed by (.isSameTable method)
 */
public class Tables implements ReadOnlyTables {

    public static final String MESSAGE_INVALID_TABLE = "Table %1$s does not exist";
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    private final UniqueTableList tableList;
    private int nextTableNumber;

    {
        tableList = new UniqueTableList();
        nextTableNumber = 1;
    }

    public Tables() {
    }

    /**
     * Creates a Tables using the tableList in the {@code toBeCopied}
     */
    public Tables(ReadOnlyTables toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    @Override
    public void setTables(List<Table> tableList) {
        this.tableList.setTables(tableList);
        nextTableNumber = this.tableList.getSize() + 1;
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

    @Override
    public boolean hasTable(Table table) {
        requireNonNull(table);
        return tableList.contains(table);
    }

    @Override
    public void addTable(Table table) {
        tableList.add(table);
        indicateModified();
    }

    @Override
    public TableNumber addTable(TableStatus tableStatus) {
        tableList.add(new Table(new TableNumber(String.valueOf(nextTableNumber)), tableStatus));
        indicateModified();
        nextTableNumber++;
        return new TableNumber(String.valueOf(nextTableNumber - 1));
    }

    @Override
    public void setTable(Table target, Table editedTable) {
        requireNonNull(editedTable);

        tableList.setTable(target, editedTable);
        indicateModified();
    }

    @Override
    public void removeTable(Table key) {
        tableList.remove(key);
        indicateModified();
    }

    @Override
    public Optional<Table> getTableFromNumber(TableNumber tableNumber) {
        return Optional.ofNullable(tableList.getTable(tableNumber));
    }

    @Override
    public boolean isOccupied(TableNumber tableNumber) throws CommandException {
        if (tableList.getTable(tableNumber) == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_TABLE, tableNumber));
        }
        return tableList.getTable(tableNumber).isOccupied();
    }

    @Override
    public boolean isRestaurantEmpty() {
        return tableList.isRestaurantEmpty();
    }

    /**
     * Notifies listeners that the RestOrRant has been modified.
     */
    public void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    @Override
    public ObservableList<Table> getTableList() {
        return tableList.asUnmodifiableObservableList();
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

    @Override
    public int hashCode() {
        return tableList.hashCode();
    }

    @Override
    public String toString() {
        return tableList.asUnmodifiableObservableList().size() + " tables";
    }
}
