package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class SectorContainsKeywordsPredicate implements Predicate<Company> {
    private final List<String> keywords;

    public SectorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Company company) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(company.getOccupation().value, keyword));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SectorContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SectorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
