package seedu.address.model.table;

import java.util.List;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Unmodifiable view of the RestOrRant's Tables.
 */
public interface ReadOnlyTables extends Observable {

    /**
     * Checks and returns true of all tables are unoccupied.
     */
    boolean isRestaurantEmpty();

    /**
     * Returns an unmodifiable view of the tables list.
     */
    ObservableList<Table> getTableList();

    /**
     * Returns true if a table with the same identity as {@code table} exists in the UniqueTableList.
     */
    boolean hasTable(Table table);

    /**
     * Adds a table to the UniqueTableList.
     * The table must not already exist in the UniqueTableList.
     */
    void addTable(Table table);

    /**
     * Adds a table to the UniqueTableList.
     * The table must not already exist in the UniqueTableList.
     */
    TableNumber addTable(TableStatus tableStatus);

    /**
     * Takes in a TableNumber and returns the Optional of a Table if table exists, else an empty Optional.
     */
    Optional<Table> getTableFromNumber(TableNumber tableNumber);

    /**
     * Replaces the contents of the table list with {@code tableList}.
     * {@code tableList} must not contain duplicate tables.
     */
    void setTables(List<Table> tableList);

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setTable(Table target, Table editedTable);

    /**
     * Removes {@code key} from this {@code RestOrRant}.
     * {@code key} must exist in the address book.
     */
    void removeTable(Table key);

    /**
     * Returns true if the table with {@code tableNumber} has at least one seat taken.
     */
    boolean isOccupied(TableNumber tableNumber) throws CommandException;
}
