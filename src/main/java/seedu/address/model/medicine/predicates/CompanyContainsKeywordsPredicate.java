package seedu.address.model.medicine.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.medicine.Medicine;

/**
 * Tests that a {@code Medicine}'s {@code Company} matches any of the keywords given.
 */
public class CompanyContainsKeywordsPredicate implements Predicate<Medicine> {
    private final List<String> keywords;

    public CompanyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Medicine medicine) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medicine.getCompany().companyName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompanyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CompanyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
