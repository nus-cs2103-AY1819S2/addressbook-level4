package seedu.address.model.pdf;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Pdf's value in the computer.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS = "Locations of file can take any values, "
            + "and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final Path value;

    /**
     * Constructs an {@code Address}.
     *
     * @param location A valid address.
     */
    public Location(String location) throws IOException {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = Paths.get(location).toRealPath(LinkOption.NOFOLLOW_LINKS);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
