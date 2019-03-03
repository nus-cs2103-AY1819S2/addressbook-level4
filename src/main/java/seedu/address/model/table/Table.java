package seedu.address.model.table;

/**
 * Represents a table in RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Table {
    
    private TableNumber tableNumber;
    private TableOccupancy tableOccupancy;

    public Table(int tableNumber, int numberOfSeats) {
        this.tableNumber = new TableNumber(tableNumber);
        this.tableOccupancy = new TableOccupancy(numberOfSeats);
    }

    public TableOccupancy getOccupancy() {
        return tableOccupancy;
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
