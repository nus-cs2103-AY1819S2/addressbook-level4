package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Gender} matches any of the keywords given.
 */

public class GenderContainsKeywordsPredicate implements Predicate<Person> {
    private final String keywords;

    public GenderContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.equalsIgnoreCase(person.getGender().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GenderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
