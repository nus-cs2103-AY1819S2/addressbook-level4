package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;


/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate extends ContainsKeywordsPredicate<Person> {

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public AddressContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Person person) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringCaseSensitive(person.getAddress().toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getAddress().toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringCaseSensitive(person.getAddress().toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getAddress().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
