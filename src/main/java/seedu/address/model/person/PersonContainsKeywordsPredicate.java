package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getClass().getSimpleName(), keyword)
                        || StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getRemark().value, keyword)
                        || (person instanceof Seller
                        && (StringUtil.containsWordIgnoreCase(((Seller) person).getAddress().value, keyword)
                        || ((Seller) person).getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))))
                        || (person instanceof Landlord
                        && (StringUtil.containsWordIgnoreCase(((Landlord) person).getAddress().value, keyword)
                        || ((Landlord) person).getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
