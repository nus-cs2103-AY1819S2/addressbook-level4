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
     * Returns an unmodifiable view of the tables list.
     */
    ObservableList<Table> getTableList();

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
     * Returns true if the table with {@code tableNumber} has at least one seat taken.
     */
    boolean isOccupied(TableNumber tableNumber) throws CommandException;
}
