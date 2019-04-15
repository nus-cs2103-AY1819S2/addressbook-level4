package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public DescriptionContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getPatientDesc()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getPatientDesc()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getPatientDesc()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getPatientDesc().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
