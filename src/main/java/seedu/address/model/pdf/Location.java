package seedu.address.model.pdf;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pdf's value in the computer.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS = "Locations of file can take any values, "
            + "and it should not be blank";

    /*
     * The address of the pdf file must end with .pdf ,
     */
    public static final String VALIDATION_REGEX = ".*\\.pdf";

    private final Path value;

    /**
     * Constructs an {@code Address}.
     *
     * @param location A valid address.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = Paths.get(location);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX) &&
                Paths.get(test).toFile().exists();
    }

    public String getLocation() {
        return this.value.toString();
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
