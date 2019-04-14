package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate extends ContainsKeywordsPredicate<Person> {

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public TagsContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Person person) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordCaseSensitive(tag.getTagName(), keyword)));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword)));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordCaseSensitive(tag.getTagName(), keyword)));

        } else {
            return keywords.stream()
                .allMatch(keyword -> person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword)));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
