package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the identification number of a Batch.
 * Guarantees: immutable; is valid as declared in {@link #isValidBatchNumber(String)}
 */
public class BatchNumber {
    public static final int MAX_LENGTH_BATCHNUMBER = 30;
    public static final String MESSAGE_CONSTRAINTS = "Batch number may contain alphanumeric characters, '-', and"
            + " spaces.\nIt should start with an alphanumeric character and should not be blank\n"
            + "Max length: " + MAX_LENGTH_BATCHNUMBER + " characters.";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} -]*";

    public final String batchNumber;

    /**
     * Constructs a {@code Name}.
     *
     * @param batchNumber A valid batch number.
     */
    public BatchNumber(String batchNumber) {
        requireNonNull(batchNumber);
        checkArgument(isValidBatchNumber(batchNumber), MESSAGE_CONSTRAINTS);
        this.batchNumber = batchNumber;
    }

    /**
     * Returns true if a given string is a valid batch number.
     */
    public static boolean isValidBatchNumber(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH_BATCHNUMBER;
    }

    @Override
    public String toString() {
        return batchNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchNumber // instanceof handles nulls
                && batchNumber.equals(((BatchNumber) other).batchNumber)); // state check
    }

    @Override
    public int hashCode() {
        return batchNumber.hashCode();
    }
}

