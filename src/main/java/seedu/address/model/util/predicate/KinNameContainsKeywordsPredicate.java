package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s attached {@code NextOfKin} {@code Name} matches any of the keywords given.
 */
public class KinNameContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public KinNameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public KinNameContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getName()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getName()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getName()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getName()
                    .toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof KinNameContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((KinNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
