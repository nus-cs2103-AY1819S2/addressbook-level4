package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Created for holding the graduation degree of the person
 */
public class Degree {

    public static final String MESSAGE_CONSTRAINTS =
            "Degree should be the highest level of completed education, and it should not be blank \n"
                    + "Can only be either: High school, Associates, Bachelors, Masters or PHD";

    /*
     * The first character of the Degree must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    //public static final String VALIDATION_REGEX = ;

    public final String value;

    /**
     * Constructs a {@code Degree}.
     *
     * @param degree A valid degree.
     */
    public Degree(String degree) {
        requireNonNull(degree);
        checkArgument(isValidDegree(degree), MESSAGE_CONSTRAINTS);
        value = degree;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDegree(String test) {

        if (test.equalsIgnoreCase("High School")) {
            return true;
        }
        if (test.equalsIgnoreCase("Associates")) {
            return true;
        }
        if (test.equalsIgnoreCase("Bachelors")) {
            return true;
        }
        if (test.equalsIgnoreCase("Masters")) {
            return true;
        }
        if (test.equalsIgnoreCase("PHD")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    //unsure if needed bc people can have the same???
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Degree // instanceof handles nulls
                && value.equals(((Degree) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


