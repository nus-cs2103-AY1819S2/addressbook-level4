package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.commands.exceptions.CommandException;

public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers\nand the number of seats taken is less than number of seats.";

    public static final String MESSAGE_INVALID_NUMBER_OF_CUSTOMERS = 
            "Table unable to accommodate number of customers provided.";

    public final String numberOfSeats;
    public String numberOfTakenSeats;
    public static final String SEATS_VALIDATION_REGEX = "\\d+";
    public static final String STATUS_VALIDATION_REGEX = "\\d+/\\d+";

    /**
     * Constructs a {@code TableStatus}/
     * 
     * @param tableStatus A valid tableStatus.
     */
    public TableStatus(String tableStatus) {
        requireNonNull(tableStatus);
        checkArgument(isValidTableStatus(tableStatus), MESSAGE_CONSTRAINTS);
        this.numberOfSeats = tableStatus.substring(2);
        this.numberOfTakenSeats = "0";
    }
    
    public void setTableStatus(String newNumberOfTakenSeats) throws CommandException {
        checkArgument(isValidNumberOfSeats(newNumberOfTakenSeats), MESSAGE_CONSTRAINTS);
        if (Integer.parseInt(newNumberOfTakenSeats) > Integer.parseInt(numberOfSeats)) {
            throw new CommandException(MESSAGE_INVALID_NUMBER_OF_CUSTOMERS);
        }
        this.numberOfTakenSeats = newNumberOfTakenSeats;
    }

    /**
     * Returns true if a given string is a valid table status.
     */
    public static boolean isValidTableStatus(String test) {
        return test.matches(STATUS_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid number of seats.
     */
    public static boolean isValidNumberOfSeats(String test) {
        return test.matches(SEATS_VALIDATION_REGEX);
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
