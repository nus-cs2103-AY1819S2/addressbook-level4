package seedu.address.model.table;

public class TableStatus {

    private int numberOfSeats;

    private int numberOfTakenSeats;

    public TableStatus(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        this.numberOfTakenSeats = 0;
    }

    public boolean equals(TableStatus otherTableStatus) {
        if (numberOfSeats == otherTableStatus.numberOfSeats
                && numberOfTakenSeats == otherTableStatus.numberOfTakenSeats) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + numberOfTakenSeats + "/" + numberOfSeats + "]";
    }
}
