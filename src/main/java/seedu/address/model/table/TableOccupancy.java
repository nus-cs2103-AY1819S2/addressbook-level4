package seedu.address.model.table;

public class TableOccupancy {

    private int numberOfSeats;

    private int numberOfTakenSeats;

    public TableOccupancy(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        this.numberOfTakenSeats = 0;
    }

    public boolean equals(TableOccupancy otherTableOccupancy) {
        if (numberOfSeats == otherTableOccupancy.numberOfSeats
                && numberOfTakenSeats == otherTableOccupancy.numberOfTakenSeats) {
            return true;
        }

        return false;
    }
}
