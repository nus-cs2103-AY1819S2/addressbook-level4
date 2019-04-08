package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s {@code DrugAllergy} matches any of the keywords given.
 */
public class DrugAllergyContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public DrugAllergyContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public DrugAllergyContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getDrugAllergy()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getDrugAllergy().toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getDrugAllergy()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getDrugAllergy().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DrugAllergyContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((DrugAllergyContainsKeywordsPredicate) other).keywords)); // state check
    }
}
