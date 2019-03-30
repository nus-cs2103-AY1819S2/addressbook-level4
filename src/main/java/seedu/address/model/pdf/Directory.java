package seedu.address.model.pdf;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Pdf's directory in the computer.
 * Guarantees: immutable; is valid as declared in {@link #isValidDirectory(String)}
 */
public class Directory {
    public static final String MESSAGE_CONSTRAINTS = "Directories of file can take any values, "
            + "and it should not be blank";

    /*
     * The first character of the directory must not be a whitespace ,
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final Path value;

    /**
     * Constructs an {@code Directory}.
     *
     * @param directory A valid address.
     */
    public Directory(String directory) {
        requireNonNull(directory);
        checkArgument(isValidDirectory(directory), MESSAGE_CONSTRAINTS);
        value = Paths.get(directory);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidDirectory(String test) {
        return test.matches(VALIDATION_REGEX)
                && Paths.get(test).toFile().exists()
                && Paths.get(test).toFile().isDirectory();
    }

    public String getDirectory() {
        return this.value.toString();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Directory: ")
                .append(value.toString())
                .append("\n").toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Directory // instanceof handles nulls
                && value.equals(((Directory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
