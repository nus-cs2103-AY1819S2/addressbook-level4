package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents the current status of a {@code Table}.
 */
public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers\nand the number of seats taken is less than number of seats.";

    public static final String MESSAGE_INVALID_NUMBER_OF_CUSTOMERS =
            "Table unable to accommodate number of customers provided.\nNumber of seats table has is: %1$s";

    public static final String SEATS_VALIDATION_REGEX = "\\d+";
    public static final String STATUS_VALIDATION_REGEX = "\\d+/\\d+";
    public final String numberOfSeats;
    private String numberOfTakenSeats;

    /**
     * Constructs a {@code TableStatus}/
     *
     * @param tableStatus A valid tableStatus.
     */
    public TableStatus(String tableStatus) {
        requireNonNull(tableStatus);
        checkArgument(isValidTableStatus(tableStatus), MESSAGE_CONSTRAINTS);
        this.numberOfSeats = tableStatus.substring(2);
        this.numberOfTakenSeats = tableStatus.substring(0, 1);
    }

    /**
     * Checks if table is occupied.
     *
     * @return true if table is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return Integer.parseInt(numberOfTakenSeats) > 0;
    }

    /**
     * Returns true if a given string is a valid table status.
     */
    public static boolean isValidTableStatus(String test) {
        String[] splitStatus = test.split("/");
        return test.matches(STATUS_VALIDATION_REGEX)
                && Integer.parseInt(splitStatus[0]) <= Integer.parseInt(splitStatus[1]);
    }

    /**
     * Returns true if a given string is a valid number of seats.
     */
    public static boolean isValidNumberOfSeats(String test) {
        return test.matches(SEATS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof TableStatus
                && numberOfSeats.equals(((TableStatus) other).numberOfSeats) && numberOfTakenSeats
                .equals(((TableStatus) other).numberOfTakenSeats));
    }

    @Override
    public String toString() {
        return numberOfTakenSeats + "/" + numberOfSeats;
    }
}
