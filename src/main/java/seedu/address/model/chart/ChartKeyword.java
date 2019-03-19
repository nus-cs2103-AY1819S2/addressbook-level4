package seedu.address.model.chart;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a chart in TravelBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidKeyword(String)}
 */
public class ChartKeyword {

    public static final String COMMAND_WORD = "generate";
    public static final String KEYWORD_COUNTRY = "country";
    public static final String KEYWORD_RATING = "rating";
    public static final String MESSAGE_CONSTRAINTS = COMMAND_WORD + ": Generates a chart based "
            + "on either Country or Rating (case-insensitive).\n"
            + "Example: " + COMMAND_WORD + " " + KEYWORD_COUNTRY;

    private String keyword;

    public ChartKeyword(String keyword) {
        requireNonNull(keyword);
        checkArgument(isValidKeyword(keyword), MESSAGE_CONSTRAINTS);
        this.keyword = keyword;
    }

    /**
     * Returns true if a given string is a valid rating.
     */
    public static boolean isValidKeyword(String test) {
        return test.equalsIgnoreCase(KEYWORD_COUNTRY) || test.equalsIgnoreCase(KEYWORD_RATING);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChartKeyword // instanceof handles nulls
                && keyword.equals(((ChartKeyword) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

    @Override
    public String toString() {
        return keyword;
    }
}
