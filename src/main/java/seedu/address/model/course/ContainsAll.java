package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Class that checks for each module code expression,
 * at least one of the module code matches one of the
 * regular expression
 */
public class ContainsAll extends CourseRequirement {


    public ContainsAll(String description, String... regexes) {
        super(description, new Predicate<List<ModuleInfo>>() {
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
        requireAllNonNull(regexes);
        requireAllNonNull(description, regexes);
    }
}
