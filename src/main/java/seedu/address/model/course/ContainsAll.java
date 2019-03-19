package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Class that checks for each module code expression,
 * at least one of the module code matches one of the
 * regular expression
 */
public class ContainsAll extends CourseRequirement {

    private static final String REGEX_CONSTRAINTS = "Regex cannot contain space!";
    private static final String TYPE = "ContainsAll";

    public ContainsAll(String description, String... regexes) {
        super(description, TYPE + Stream.of(regexes).reduce((x, y) -> x + " " + y),
            new Predicate<List<ModuleInfo>>() {
                @Override
                public boolean test(List<ModuleInfo> moduleInfos) {
                    for (String regex : regexes) {
                        if (moduleInfos.stream().map(x -> x.getCodeString()).noneMatch(x -> x.matches(regex))) {
                            return false;
                        }
                    }
                    return true;
                }
            },
            new Function<List<ModuleInfo>, String>() {
                @Override
                public String apply(List<ModuleInfo> moduleInfos) {
                    StringBuilder sb = new StringBuilder("Failed contain:\n");
                    for (String regex: regexes) {
                        if (moduleInfos.stream().map(x -> x.getCodeString()).noneMatch(x -> x.matches(regex))) {
                            sb.append(regex + "\n");
                        }
                    }
                    return sb.toString();
                }
            }
        );
        checkArgument(isValidRegex(regexes), REGEX_CONSTRAINTS);
        requireAllNonNull(regexes);
        requireAllNonNull(description, regexes);
    }


    public static boolean isValidRegex(String... regexes) {
        return Stream.of(regexes).allMatch(x -> x.matches("\\S*"));
    }
}
