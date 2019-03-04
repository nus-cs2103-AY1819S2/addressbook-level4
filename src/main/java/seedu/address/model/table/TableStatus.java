package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers and the number of seats taken is less than number of seats";

    public final String numberOfSeats;
    public String numberOfTakenSeats;
    public static final String VALIDATION_REGEX = "[" + "^[\\d]+$" + "/" + "^[\\d]+$" + "]";

    /**
     * Constructs a {@code TableStatus}/
     * 
     * @param numberOfSeats A valid number of seats.
     */
    public TableStatus(String numberOfSeats) {
        requireNonNull(numberOfSeats);
        checkArgument(isValidTableStatus(numberOfSeats), MESSAGE_CONSTRAINTS);
        this.numberOfSeats = numberOfSeats;
        this.numberOfTakenSeats = "0";
    }

    public void setTableStatus(String newNumberOfTakenSeats) {
        this.numberOfTakenSeats = newNumberOfTakenSeats;
    }

    /**
     * Returns true if a given string is a valid table status.
     */
    public static boolean isValidTableStatus(String test) {
        String newTest = test;
        newTest = newTest.replace("[", "/");
        newTest = newTest.replace("]", "/");
        String[] split = newTest.trim().split("/");
        return test.matches(VALIDATION_REGEX)
                && ((Integer.parseInt(split[1]) <= Integer.parseInt(split[0])));
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
