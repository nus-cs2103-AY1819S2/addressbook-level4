package seedu.address.testutil;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * A utility class to help with building Table objects.
 */
public class TableBuilder {

    public static final String DEFAULT_TABLE_NUMBER = "1";
    public static final String DEFAULT_TABLE_STATUS = "0/4";

    private TableNumber tableNumber;
    private TableStatus tableStatus;

    public TableBuilder() {
        tableNumber = new TableNumber(DEFAULT_TABLE_NUMBER);
        tableStatus = new TableStatus(DEFAULT_TABLE_STATUS);
    }

    /**
     * Initializes the TableBuilder with the data of {@code toCopy}.
     */
    public TableBuilder(Table toCopy) {
        tableNumber = toCopy.getTableNumber();
        tableStatus = toCopy.getTableStatus();
    }

    /**
     * Sets the {@code TableNumber} of the {@code Table} that we are building.
     */
    public TableBuilder withTableNumber(String tableNumber) {
        this.tableNumber = new TableNumber(tableNumber);
        return this;
    }

    /**
     * Sets the {@code TableStatus} of the {@code Table} that we are building.
     */
    public TableBuilder withTableStatus(String tableStatus) {
        this.tableStatus = new TableStatus(tableStatus);
        return this;
    }

    public Table build() {
        return new Table(tableNumber, tableStatus);
    }

}
