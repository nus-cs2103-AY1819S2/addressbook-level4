package seedu.address.model.person;

import java.util.Arrays;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {
    public static final String MESSAGE_CONSTRAINTS = "Not a valid gender.";
    private static final String[] POSSIBLE_GENDERS = {"M","F","MALE","FEMALE"};
    private static final TreeSet<String> POSSIBLE_GENDERS_TREE = new TreeSet<>(Arrays.asList(POSSIBLE_GENDERS));
    public final char value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        value = gender.charAt(0);
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        test = test.toUpperCase();
        if (test == null) {
            throw new NullPointerException("Parameter Type cannot be null");
        }
        return POSSIBLE_GENDERS_TREE.contains(test);
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && value == (((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
