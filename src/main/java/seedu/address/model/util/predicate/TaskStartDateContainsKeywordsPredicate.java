package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;


/**
 * Tests that a {@code Task}'s {@code StartDate} matches any of the keywords given.
 */
public class TaskStartDateContainsKeywordsPredicate extends ContainsKeywordsPredicate<Task> {

    public TaskStartDateContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getStartDate()
                .toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskStartDateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskStartDateContainsKeywordsPredicate) other).keywords)); // state check
    }
}
