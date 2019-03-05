package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.commands.exceptions.CommandException;

public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers and the number of seats taken is less than number of seats.";

    public static final String MESSAGE_INVALID_NUMBER_OF_CUSTOMERS = 
            "Table unable to accommodate number of customers provided.";

    public final String numberOfSeats;
    public String numberOfTakenSeats;
    public static final String VALIDATION_REGEX = "\\d+";

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

    public void setTableStatus(String newNumberOfTakenSeats) throws CommandException {
        checkArgument(isValidTableStatus(newNumberOfTakenSeats), MESSAGE_CONSTRAINTS);
        if (Integer.parseInt(newNumberOfTakenSeats) > Integer.parseInt(numberOfSeats)) {
            throw new CommandException(MESSAGE_INVALID_NUMBER_OF_CUSTOMERS);
        }
        this.numberOfTakenSeats = newNumberOfTakenSeats;
    }

    /**
     * Returns true if a given string is a valid table status.
     */
    public static boolean isValidTableStatus(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return numberOfTakenSeats + "/" + numberOfSeats;
    }
}
