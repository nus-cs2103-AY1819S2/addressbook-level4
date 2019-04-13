package seedu.address.model.prescription;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code MedicalHistory}'s {@code Name} matches any of the keywords given.
 */
public class PrescriptionContainsKeywordsPredicate implements Predicate<Prescription> {
    private final List<String> keywords;

    public PrescriptionContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Prescription prescription) {
        Boolean descriptionContainsKeyword = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        prescription.getDescription().getDescription().replaceAll("\\p{P}", " "), keyword));
        return descriptionContainsKeyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PrescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}

