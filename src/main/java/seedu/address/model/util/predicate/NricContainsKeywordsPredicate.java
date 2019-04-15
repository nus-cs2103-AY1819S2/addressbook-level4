package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s {@code Nric} matches any of the keywords given.
 */
public class NricContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public NricContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public NricContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringCaseSensitive(patient.getNric().toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(patient.getNric().toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringCaseSensitive(patient.getNric().toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(patient.getNric().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NricContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((NricContainsKeywordsPredicate) other).keywords)); // state check
    }
}
