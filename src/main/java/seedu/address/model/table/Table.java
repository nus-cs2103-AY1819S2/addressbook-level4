package seedu.address.model.table;

/**
 * Represents a table in RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Table {
    
    private TableNumber tableNumber;
    private TableStatus tableStatus;

    public Table(int tableNumber, int numberOfSeats) {
        this.tableNumber = new TableNumber(tableNumber);
        this.tableStatus = new TableStatus(numberOfSeats);
    }

    public TableStatus getOccupancy() {
        return tableStatus;
    }

    public int getTableNumber() {
        return tableNumber.getTableNumber();
    }

    public boolean isSameTable(Table otherTable) {
        if (otherTable == this) {
            return true;
        }

        return otherTable != null
                && otherTable.getTableNumber() == getTableNumber()
                && otherTable.getOccupancy().equals(getOccupancy());
    }
}
