package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends ContainsKeywordsPredicate<Person> {

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public PhoneContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Person person) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(person.getPhone().toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(person.getPhone().toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof PhoneContainsKeywordsPredicate
            && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords));
    }
}
