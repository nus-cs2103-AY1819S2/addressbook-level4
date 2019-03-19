package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Known Programming Languages in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidKnownProgLang(String)}
 */
public class KnownProgLang {

    public static final String MESSAGE_CONSTRAINTS = "Known programming languages can take any values, "
            + "and it should not be blank";

    /*
     * The first character of the programming language must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code KnownProgLang}.
     *
     * @param progLang A valid programming language.
     */
    public KnownProgLang(String progLang) {
        requireNonNull(progLang);
        checkArgument(isValidKnownProgLang(progLang), MESSAGE_CONSTRAINTS);
        value = progLang;
    }

    /**
     * Returns true if a given string is a valid programming language.
     */
    public static boolean isValidKnownProgLang(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KnownProgLang // instanceof handles nulls
                && value.equals(((KnownProgLang) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
