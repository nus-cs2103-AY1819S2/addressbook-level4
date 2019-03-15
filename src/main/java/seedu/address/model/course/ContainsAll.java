package seedu.address.model.course;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Class that checks for each module code expression,
 * at least one of the module code matches one of the
 * regular expression
 */
public class ContainsAll implements CourseRequirement {

    private final String description;
    private final List<String> regexes;

    public ContainsAll(String description, String... regexes) {
        this.description = description;
        this.regexes = List.of(regexes);
    }

    @Override
    public boolean test(List<ModuleInfo> modules) {
        for (String regex: regexes) {
            if (modules.stream().map(x -> x.getCodeString()).noneMatch(x -> x.matches(regex))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> modules) {
        StringBuilder sb = new StringBuilder("Failed contain:\n");
        for (String regex: regexes) {
            if (modules.stream().map(x -> x.getCodeString()).noneMatch(x -> x.matches(regex))) {
                sb.append(regex + "\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
