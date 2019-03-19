package seedu.address.model.activity;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Activity}'s {@code ActivityName} matches any of the keywords given.
 */
public class ActivityNameContainsKeywordsPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    public ActivityNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Activity activity) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getName().fullActivityName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.activity.ActivityNameContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((seedu.address.model.activity.ActivityNameContainsKeywordsPredicate) other)
                .keywords)); // state check
    }
}
