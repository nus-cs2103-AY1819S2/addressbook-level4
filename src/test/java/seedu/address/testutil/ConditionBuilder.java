package seedu.address.testutil;

import java.util.regex.Pattern;

import seedu.address.model.course.Condition;

/**
 * A utility class to help build Condition objects
 */
public class ConditionBuilder {

    public static final int DEFAULT_MIN_TO_SATISFY = 1;
    public static final Pattern DEFAULT_PATTERN = Pattern.compile("GEH1[0-9]{3}[A-Z]?");
    private int minToSatisfy;
    private Pattern pattern;

    public ConditionBuilder() {
        this.minToSatisfy = DEFAULT_MIN_TO_SATISFY;
        this.pattern = DEFAULT_PATTERN;
    }

    /**
     * Initializes the ModuleTakenBuilder with the data of {@code moduleTakenToCopy}.
     */
    public ConditionBuilder(Condition condition) {
        this.minToSatisfy = condition.getMinToSatisfy();
        this.pattern = condition.getPattern();
    }


    /**
     * Sets the minToSatisfy of the condition to given minToSatisfy
     */
    public ConditionBuilder withMin(int minToSatisfy) {
        this.minToSatisfy = minToSatisfy;
        return this;
    }

    /**
     * Sets the pattern of condition to given String
     */
    public ConditionBuilder withPattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
        return this;
    }


    /**
     * Sets the pattern of condition to given Pattern
     */
    public ConditionBuilder withPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public Condition build() {
        return new Condition(minToSatisfy, pattern.toString());
    }

}
