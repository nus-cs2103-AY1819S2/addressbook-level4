package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a File name in MediTabs.
 * Guarantees: immutable; is valid as declared in {@link #isValidFileName(String)}
 */
public class FileName {

    public static final String MESSAGE_CONSTRAINTS =
            "File Names (without including file format) should only start with alphanumeric characters following "
                    + "which can contain alphanumeric characters "
                    + "or \"_\" or \"-\" (without quotation marks) and it should not be blank";

    /*
     * The characters of the file name must not contain any whitespace
     * and does not contain certain symbols like \/:*?"<>|.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_-]*";

    public final String fullFileName;

    /**
     * Constructs a {@code FileName}.
     *
     * @param fileName A valid file name.
     */
    public FileName(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidFileName(fileName), MESSAGE_CONSTRAINTS);
        fullFileName = fileName;
    }

    /**
     * Returns true if a given string is a valid file name.
     */
    public static boolean isValidFileName(String test) {
        if (test.isEmpty()) {
            return false;
        }
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullFileName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileName // instanceof handles nulls
                && fullFileName.equals(((FileName) other).fullFileName)); // state check
    }

    @Override
    public int hashCode() {
        return fullFileName.hashCode();
    }

}
