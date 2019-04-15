package seedu.address.model.project;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Project}'s {@code ProjectName, ProjectDescription} matches any of the keywords given.
 */
public class ProjectContainsKeywordsPredicate implements Predicate<Project> {

    private final List<String> keywords;

    public ProjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Project project) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(project.getProjectName().projectName, keyword)
                || StringUtil.containsWordIgnoreCase(project.getDescription().description, keyword)
                || StringUtil.containsWordIgnoreCase(project.getClient().client, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ProjectContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((ProjectContainsKeywordsPredicate) other).keywords)); // state check
    }
}

