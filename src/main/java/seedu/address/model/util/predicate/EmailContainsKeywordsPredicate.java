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

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().toString(),
            keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }
}
