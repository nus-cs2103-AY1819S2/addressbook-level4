package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Batches' number in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidBatchNumber(String)}
 */
public class BatchNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Batch number may contain alphanumeric characters, '-', and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} -]*";

    public final String number;

    /**
     * Constructs a {@code Name}.
     *
     * @param batchNumber A valid batch number.
     */
    public BatchNumber(String batchNumber) {
        requireNonNull(batchNumber);
        checkArgument(isValidBatchNumber(batchNumber), MESSAGE_CONSTRAINTS);
        number = batchNumber;
    }

    /**
     * Returns true if a given string is a valid batch number.
     */
    public static boolean isValidBatchNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.medicine.Name // instanceof handles nulls
                && number.equals(((seedu.address.model.medicine.Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}

