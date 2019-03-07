package seedu.address.model.table;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents the current status of a {@code Table}.
 */
public class TableStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Table status should only contain numbers\nand the number of seats taken is less than number of seats.";

    public static final String MESSAGE_INVALID_NUMBER_OF_CUSTOMERS = 
            "Table unable to accommodate number of customers provided.\nNumber of seats table has is: %1$s";

    private String numberOfTakenSeats;
    public final String numberOfSeats;
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
            throw new CommandException(String.format(MESSAGE_INVALID_NUMBER_OF_CUSTOMERS, numberOfSeats));
        }
        this.numberOfTakenSeats = newNumberOfTakenSeats;
    }

    public boolean isOccupied() {
        return Integer.parseInt(numberOfTakenSeats) > 0;
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

    @Override
    public String toString() {
        return numberOfTakenSeats + "/" + numberOfSeats;
    }
}
