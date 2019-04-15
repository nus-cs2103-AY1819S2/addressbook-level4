package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;


/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends ContainsKeywordsPredicate<Person> {

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }


    public EmailContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Person person) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringCaseSensitive(person.getEmail().toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getEmail().toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringCaseSensitive(person.getEmail().toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getEmail().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }
}
