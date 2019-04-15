package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsCpnyKeywordsPredicate implements Predicate<Company> {
    private final List<String> keywords;

    public NameContainsCpnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Company company) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(company.getName().fullName, keyword));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsCpnyKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsCpnyKeywordsPredicate) other).keywords)); // state check
    }

}

