package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Represents Condition of a PrimitiveRequirement
 */
public class Condition {
    public static final String INVALID_REGEXES =
            "At least one of the regular expressions is invalid!";
    public static final String INVALID_MIN_TO_SATISFY =
            "Minimum number of modules to satisfy aspect cannot be less than 1!";
    private final Pattern pattern;
    private final int minToSatisfy;

    /**
     * Constructs a {@code Condition}
     * @param minToSatisfy minimum number of modules that matches pattern to satisfy condition
     * @param pattern a regex to check whether ModuleInfoCode matches
     */
    public Condition(int minToSatisfy, String pattern) {
        requireAllNonNull(minToSatisfy, pattern);
        checkArgument(isValidMinToSatisfy(minToSatisfy), INVALID_MIN_TO_SATISFY);
        checkArgument(isValidRegex(pattern), INVALID_REGEXES);
        this.minToSatisfy = minToSatisfy;
        this.pattern = Pattern.compile(pattern);
    }


    /**
     * Alternate constructor that only takes in a pattern string.
     * minToSatisfy is immediately set to 1
     * @param pattern a regex to check whether ModuleInfoCode matches
     */
    public Condition(String pattern) {
        checkArgument(isValidRegex(pattern), INVALID_REGEXES);
        this.pattern = Pattern.compile(pattern);
        this.minToSatisfy = 1;
    }

    /**
     * Returns true if given number is larger than 0.
     */
    public static boolean isValidMinToSatisfy(int test) {
        return test > 0;
    }

    /**
     * Returns true pattern passed is valid Pattern.
     */
    public static boolean isValidRegex(String test) {
        try {
            Pattern.compile(test);
        } catch (PatternSyntaxException exception) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if there are at least minToSatisfy number of modulesInfoCodes
     * that matches pattern
     * @param moduleInfoCodes a list of module codes to check whether condition is satisfied
     * @return true if condition satisfied, false otherwise
     */
    public boolean isSatisfied(List<ModuleInfoCode> moduleInfoCodes) {
        return moduleInfoCodes.stream()
                .map(ModuleInfoCode::toString)
                .filter(str-> pattern.matcher(str).matches())
                .distinct()
                .count() >= minToSatisfy;
    }

    /**
     * Returns true if the module code of module matches at least one of the regex in regex list
     * @param moduleInfoCode a module code to check against regex list
     * @return true if at least module code of module matches at least one of the regex in regex list
     */
    public boolean canSatisfy(ModuleInfoCode moduleInfoCode) {
        return pattern.matcher(moduleInfoCode.toString()).matches();
    }

    /**
     * Returns number of distinct modules in list of ModuleInfoCodes that satisfy this requirement
     * @param moduleInfoCodes a list of module codes to check how many distinct elements in the list matches pattern
     * @return number of distinct module module codes that matches pattern
     */
    public int getNumCompleted(List<ModuleInfoCode> moduleInfoCodes) {
        return Math.toIntExact(Math.min(moduleInfoCodes.stream()
                .map(ModuleInfoCode::toString)
                .filter(str-> pattern.matcher(str).matches())
                .distinct()
                .count(), minToSatisfy));
    }

    public int getMinToSatisfy() {
        return minToSatisfy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Condition)) {
            return false;
        }
        Condition other = (Condition) obj;
        return this.pattern.toString().equals(other.pattern.toString())
                && this.minToSatisfy == other.minToSatisfy;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
