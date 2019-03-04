package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers and the number of seats taken is less than number of seats";

    public final String numberOfSeats;
    public final String numberOfTakenSeats;
    public static final String VALIDATION_REGEX = "[" + "^[\\d]+$" + "/" + "^[\\d]+$" + "]";

    /**
     * Constructs a {@code TableStatus}/
     * 
     * @param numberOfSeats A valid number of seats.
     */
    public TableStatus(String numberOfSeats) {
        requireNonNull(numberOfSeats);
        checkArgument(isValidTableStatus(this), MESSAGE_CONSTRAINTS);
        this.numberOfSeats = numberOfSeats;
        this.numberOfTakenSeats = "0";
    }

    /**
     * Returns true if a given string is a valid table status.
     */
    public static boolean isValidTableStatus(TableStatus test) {
        return test.toString().matches(VALIDATION_REGEX)
                && (Integer.parseInt(test.numberOfTakenSeats) <= Integer.parseInt(test.numberOfSeats));
    }

    public boolean equals(TableStatus otherTableStatus) {
        if (numberOfSeats.equals(otherTableStatus.numberOfSeats)
                && numberOfTakenSeats.equals(otherTableStatus.numberOfTakenSeats)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + numberOfTakenSeats + "/" + numberOfSeats + "]";
    }
}
