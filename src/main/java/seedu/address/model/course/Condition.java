package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Represents aspect of a CourseRequirement
 */
public class Condition {

    public static final String INVALID_REGEXES =
            "At least one of the regular expressions is invalid or contains comma!";
    public static final String INVALID_REGEXES_SIZE =
            "Must contain at least 1 regex expression!";
    public static final String INVALID_MIN_TO_SATISFY =
            "Minimum number of modules to satisfy aspect cannot be less than 1!";
    private final String conditionName;
    private final List<String> regexes;
    private final int minToSatisfy;

    /**
     * Constructs a {@code Condition}
     * @param minToSatisfy minimum number of modules that matches regex to satisfy condition
     * @param conditionName name of the condition
     * @param regexes list of regular expression
     */
    public Condition(int minToSatisfy, String conditionName, String... regexes) {
        requireAllNonNull(minToSatisfy, conditionName, regexes);
        requireAllNonNull((Object[]) regexes);
        checkArgument(isValidMinToSatisfy(minToSatisfy), INVALID_MIN_TO_SATISFY);
        checkArgument(isValidRegex(regexes), INVALID_REGEXES);
        checkArgument(isValidRegexesSize(regexes.length), INVALID_REGEXES_SIZE);
        this.conditionName = conditionName;
        this.regexes = List.of(regexes);
        this.minToSatisfy = minToSatisfy;
    }


    /**
     * Alternate constructor that only takes in a list of regex.
     * minToSatisfy is immediately set to number of regex input
     * @param conditionName name of the condition to be satisfied
     * @param regexes list of regular expression
     */
    public Condition(String conditionName, String... regexes) {
        requireAllNonNull(conditionName, regexes);
        requireAllNonNull((Object[]) regexes);
        checkArgument(isValidRegex(regexes), INVALID_REGEXES);
        checkArgument(isValidRegexesSize(regexes.length), INVALID_REGEXES_SIZE);
        this.conditionName = conditionName;
        this.regexes = List.of(regexes);
        this.minToSatisfy = regexes.length;
    }

    /**
     * Returns true if given number is larger than 0.
     */
    public static boolean isValidMinToSatisfy(int test) {
        return test > 0;
    }

    /**
     * Returns true if given size is larger than 0.
     */
    public static boolean isValidRegexesSize(int test) {
        return test > 0;
    }

    /**
     * Returns true if given in list of regex, none of the regex contains "," or
     * is invalid regex.
     */
    public static boolean isValidRegex(String... regexes) {
        try {
            Stream.of(regexes).forEach(str -> Pattern.compile(str));
        } catch (PatternSyntaxException exception) {
            return false;
        }
        return Stream.of(regexes).noneMatch(str -> str.contains(","));
    }

    /**
     * Returns true if there are at least minToSatisfy number of modules in list
     * matches regex in regexes list
     * @param moduleInfoCodes a list of module codes to check whether condition is satisfied
     * @return true if condition satisfied, false otherwise
     */
    public boolean isSatisfied(List<ModuleInfoCode> moduleInfoCodes) {
        //Will complete if modules taken list class is completed
        return moduleInfoCodes.stream()
                .filter(moduleInfoCode -> regexes.stream().anyMatch(regex -> moduleInfoCode.toString().matches(regex)))
                .distinct().count() >= minToSatisfy;
    }

    /**
     * Returns true if the module code of module matches at least one of the regex in regex list
     * @param moduleInfoCode a module code to check against regex list
     * @return true if at least module code of module matches at least one of the regex in regex list
     */
    public boolean canSatisfy(ModuleInfoCode moduleInfoCode) {
        return regexes.stream().anyMatch(regex -> moduleInfoCode.toString().matches(regex));
    }

    /**
     * Returns percentage, in range of [0, 1.0] of completion for this condition
     * @param moduleInfoCodes a list of module codes to check completion percentage
     * @return a double in range of [0,1.0] to see percentage of completion
     */
    public double getPercentageCompleted(List<ModuleInfoCode> moduleInfoCodes) {
        return Math.max(moduleInfoCodes.stream()
                .filter(moduleInfoCode -> regexes.stream().anyMatch(regex -> moduleInfoCode.toString().matches(regex)))
                .distinct().count() / (double) minToSatisfy, 1.0);
    }

    /**
     * If the condition is unsatisfied, returns formatted string of the regex not satisfied
     * @param moduleInfoCodes a list of module codes to unsatisfied modules
     * @return a formatted String of regexes that are not fulfilled by any of the module codes
     */
    public String getUnsatisfied(List<ModuleInfoCode> moduleInfoCodes) {
        if (isSatisfied(moduleInfoCodes)) {
            return conditionName + " is satisfied";
        }
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(conditionName + " has unsatisfied:");
        regexes.stream()
            .filter(regex -> moduleInfoCodes.stream()
                    .noneMatch(moduleInfoCode -> moduleInfoCode.toString().matches(regex)))
            .forEach(regex -> formattedString.append("\n regex"));
        return formattedString.toString();
    }

    public String getConditionName() {
        return conditionName;
    }

    public int getMinToSatisfy() {
        return minToSatisfy;
    }

    public List<String> getRegexes() {
        return regexes;
    }
}
