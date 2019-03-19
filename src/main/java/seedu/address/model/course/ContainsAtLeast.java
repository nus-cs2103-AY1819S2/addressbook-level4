package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Checks number of modules that
 */
public class ContainsAtLeast extends CourseRequirement {

    private static final String NUMBER_CONSTRAINTS = "Number of modules must be at least 0!";
    private static final String REGEX_CONSTRAINTS = "Regex cannot contain space!";
    private static final String TYPE = "ContainsAtLeast";

    public ContainsAtLeast(String description, int number, String... regexes) {
        super(description, TYPE + number + Stream.of(regexes).reduce((x, y) -> x + " " + y),
            new Predicate<List<ModuleInfo>>() {
                @Override
                public boolean test(List<ModuleInfo> moduleInfos) {
                    return moduleInfos.stream()
                        .filter(x -> Stream.of(regexes).anyMatch(y -> x.getCodeString().matches(y)))
                        .count() >= number;
                }
            },
            new Function<List<ModuleInfo>, String>() {
                @Override
                public String apply(List<ModuleInfo> moduleInfos) {
                    //TODO: find out a good way to do this;
                    StringBuilder sb = new StringBuilder();
                    return sb.toString();
                }
            }
        );
        checkArgument(isValidNumber(number), NUMBER_CONSTRAINTS);
        checkArgument(isValidRegex(regexes), REGEX_CONSTRAINTS);
        requireAllNonNull(regexes);
        requireAllNonNull(number, description, regexes);

    }

    public static boolean isValidNumber(int number) {
        return number >= 0;
    }

    public static boolean isValidRegex(String... regexes) {
        return Stream.of(regexes).allMatch(x -> x.matches("\\S*"));
    }
}
