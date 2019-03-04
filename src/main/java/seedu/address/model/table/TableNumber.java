package seedu.address.model.table;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Table's table number in the RestOrRant.
 * Guarantees: immutable; is valid as declared in {@link #isValidTableNumber(String)}
 */
public class TableNumber {

    public static final String MESSAGE_CONSTRAINTS = 
            "Table Number should only contain Integers greater than zero.";

    public final String tableNumber;
    
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    /**
     * Constructs a {@code TableNumber}/
     * 
     * @param tableNumber A valid tableNumber.
     */
    public TableNumber(String tableNumber) {
        checkArgument(isValidTableNumber(tableNumber), MESSAGE_CONSTRAINTS);
        this.tableNumber = tableNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns true if a given string is a valid table number.
     */
    public static boolean isValidTableNumber(String tableNumber) {
        return tableNumber.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return tableNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TableNumber
                && tableNumber.equals(((TableNumber) other).getTableNumber()));
    }

    @Override
    public int hashCode() {
        return tableNumber.hashCode();
    }
}
