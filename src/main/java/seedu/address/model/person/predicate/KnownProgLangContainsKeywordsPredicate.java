package seedu.address.model.person.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Gender} matches any of the keywords given.
 */
public class KnownProgLangContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public KnownProgLangContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        return (keywords == null) || keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(StringUtil.getSetString
                (person.getKnownProgLangs()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof KnownProgLangContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((KnownProgLangContainsKeywordsPredicate) other).keywords)); // state check
    }

}
