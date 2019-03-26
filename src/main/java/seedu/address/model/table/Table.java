package seedu.address.model.table;

/**
 * Represents a table in RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Table {

    private final TableNumber tableNumber;
    private final TableStatus tableStatus;

    public Table(String tableNumber, String numberOfSeats) {
        this.tableNumber = new TableNumber(tableNumber);
        this.tableStatus = new TableStatus(numberOfSeats);
    }

    public Table(TableNumber tableNumber, TableStatus tableStatus) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    public TableStatus getTableStatus() {
        return tableStatus;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public boolean isOccupied() {
        return tableStatus.isOccupied();
    }

    /**
     * Checks if the current table is the same as table provided
     *
     * @param otherTable other table  to be checked against
     * @return true if tables have the same TableNumber; false otherwise
     */
    public boolean isSameTable(Table otherTable) {
        return otherTable == this
                || (otherTable != null
                && otherTable.getTableNumber().equals(this.getTableNumber()));
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + ": " + tableStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Table
                && tableNumber.equals(((Table) other).tableNumber)
                && tableStatus.equals(((Table) other).tableStatus));
    }
}
