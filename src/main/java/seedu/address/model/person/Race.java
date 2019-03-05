package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Represents a Person's race in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRace(String)}
 */
public class Race {


    public static final String MESSAGE_CONSTRAINTS =
            "Not among list of possible races.";
    private static final String[] POSSIBLE_RACES = {"CHINESE", "MALAY", "INDIAN", "OTHERS"};
    private static final TreeSet<String> POSSIBLE_RACES_TREE = new TreeSet<>(Arrays.asList(POSSIBLE_RACES));
    public final String value;

    /**
     * Constructs a {@code Race}.
     *
     * @param race A valid race.
     */
    public Race(String race) {
        requireNonNull(race);
        checkArgument(isValidRace(race), MESSAGE_CONSTRAINTS);
        value = race;
    }

    /**
     * Returns true if a given string is a valid race.
     */
    public static boolean isValidRace(String test) {
        if (test == null) {
            throw new NullPointerException("Parameter Type cannot be null");
        }
        return POSSIBLE_RACES_TREE.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Race // instanceof handles nulls
                && value.equals(((Race) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
