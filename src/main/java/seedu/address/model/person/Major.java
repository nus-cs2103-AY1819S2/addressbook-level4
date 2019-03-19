package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Represents a Person's major in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {


    public static final String MESSAGE_CONSTRAINTS =
            "Not among list of possible majors.";
    private static final String[] POSSIBLE_MAJORS = {"CS", "ME", "MATH", "ECON", "ART", "Computer Science"};
    private static final TreeSet<String> POSSIBLE_MAJORS_TREE = new TreeSet<>(Arrays.asList(POSSIBLE_MAJORS));
    public final String value;

    /**
     * Constructs a {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_CONSTRAINTS);
        value = major;
    }

    /**
     * Returns true if a given string is a valid major.
     */
    public static boolean isValidMajor(String test) {
        if (test == null) {
            throw new NullPointerException("Parameter Type cannot be null");
        }
        return POSSIBLE_MAJORS_TREE.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
