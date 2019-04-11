package seedu.address.model.pdf;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pdf's file size in the pdf book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size implements Comparable<Size> {
    public static final String MESSAGE_CONSTRAINTS = "File size should be a non-negative number";

    public static final String VALIDATION_REGEX = "^\\d+$";

    private static final int THRESHOLD_KILOBYTE = 1024;
    private static final int THRESHOLD_MEGABIYTE = 1048576;
    private static final int THRESHOLD_GIGABYTE = 1073741824;

    private final String value;

    public Size(String size) {
        requireNonNull(size);
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        value = size;
    }

    /**
     * Returns true if a given string is a non-negative number.
     */
    public static boolean isValidSize(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getValue() {
        return this.value;
    }

    public String getReadableValue() {
        int actualValue = Integer.parseInt(this.value);
        StringBuilder sb = new StringBuilder();

        if (actualValue < THRESHOLD_KILOBYTE) {
            return sb.append(this.value)
                    .append(" b")
                    .toString();

        } else if (actualValue < THRESHOLD_MEGABIYTE) {
            return sb.append(Integer.toString((actualValue / THRESHOLD_KILOBYTE)))
                    .append(" kb")
                    .toString();
        } else if (actualValue < THRESHOLD_GIGABYTE) {
            return sb.append(Integer.toString((actualValue / THRESHOLD_MEGABIYTE)))
                    .append(" mb")
                    .toString();
        } else {
            return sb.append(Integer.toString((actualValue / THRESHOLD_GIGABYTE)))
                    .append(" gb")
                    .toString();
        }
    }

    public String toReadableString() {
        return new StringBuilder("Size: ").append(this.getReadableValue()).append("\n").toString();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Size: ").append(value).append("\n").toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Size // instanceof handles nulls
                && value.equals(((Size) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    @Override
    public int compareTo(Size other) {
        int thisFileSize = Integer.parseInt(this.value);
        int otherFileSize = Integer.parseInt(other.value);
        return thisFileSize - otherFileSize;
    }
}
