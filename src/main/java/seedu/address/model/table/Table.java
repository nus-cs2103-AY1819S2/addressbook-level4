package seedu.address.model.table;

/**
 * Represents a table in RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Table {
    
    private TableNumber tableNumber;
    private TableStatus tableStatus;

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

    public String getTableNumber() {
        return tableNumber.getTableNumber();
    }

    public boolean isSameTable(Table otherTable) {
        if (otherTable == this) {
            return true;
        }

        return otherTable != null
                && otherTable.getTableNumber().equals(getTableNumber());
    }
}
