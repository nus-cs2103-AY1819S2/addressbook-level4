package seedu.address.model.activity;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Tests that the attributes of an {@code Activity} matches any of the keywords given.
 */
public class ActivityContainsKeywordsPredicate implements Predicate<Activity> {
    private final HashMap<Prefix, List<String>> keywords;

    public ActivityContainsKeywordsPredicate(HashMap<Prefix, List<String>> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if activity contains the predicate
     */
    public boolean test(Activity activity) {
        if (keywords.containsKey(PREFIX_ALL)) {
            return keywords.get(PREFIX_ALL).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getName().fullActivityName, keyword)
                            || StringUtil.containsWordIgnoreCase(activity.getDescription().value, keyword)
                            || StringUtil.containsWordIgnoreCase(activity.getLocation().value, keyword));
        }
        boolean isNameMatched = keywords.get(PREFIX_ACTIVITYNAME) == null
                || keywords.get(PREFIX_ACTIVITYNAME).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getName().fullActivityName, keyword));

        boolean isDescriptionMatched = keywords.get(PREFIX_ADESCRIPTION) == null
                || keywords.get(PREFIX_ADESCRIPTION).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getDescription().value, keyword));

        boolean isLocationMatched = keywords.get(PREFIX_LOCATION) == null
                || keywords.get(PREFIX_LOCATION).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(activity.getLocation().value, keyword));

        return isNameMatched && isDescriptionMatched && isLocationMatched;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.activity.ActivityContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((ActivityContainsKeywordsPredicate) other).keywords)); // state check
    }

}
