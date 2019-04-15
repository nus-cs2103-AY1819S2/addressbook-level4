package seedu.address.model.skill;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code skill} matches any of the keyword given.
 */
public class EmployeeSkillContainsKeywordsPredicate implements Predicate<Employee> {
    private final List<String> keywords;

    public EmployeeSkillContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        //convert list of skills into string with no comma and brackets for matching purpose
        String prepEmployeeSkill = employee.getSkills().toString().replaceAll("," , "")
            .replaceAll("\\]", "").replaceAll("\\[", "");
        return keywords.stream().anyMatch(keyword ->
            StringUtil.containsWordIgnoreCase(prepEmployeeSkill, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EmployeeSkillContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((EmployeeSkillContainsKeywordsPredicate) other).keywords)); // state check
    }

}
