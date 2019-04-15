package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Major} matches any of the keywords given.
 */

public class MajorContainsKeywordsPredicate implements Predicate<Person> {
    private final String keywords;

    public MajorContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.equalsIgnoreCase(person.getMajor().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MajorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
