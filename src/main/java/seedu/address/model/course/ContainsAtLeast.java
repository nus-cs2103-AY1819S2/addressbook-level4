package seedu.address.model.course;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Checks from list of modules that contains at least
 * a certain threshold
 */
public class ContainsAtLeast implements CourseRequirement {

    private final String description;
    private final int number;
    private final List<String> regexes;

    public ContainsAtLeast(String description, int number, String... regexes) {
        this.description = description;
        this.number = number;
        this.regexes = List.of(regexes);
    }

    @Override
    public boolean test(List<ModuleInfo> modules) {
        return modules.stream()
                .filter(x -> regexes.stream().anyMatch(y -> x.getCodeString().matches(y)))
                .count() >= number;
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> modules) {
        //TODO:
        return "";
    }
    @Override
    public String getDescription() {
        return description;
    }

}
