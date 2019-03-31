package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s attached {@code NextOfKin} {@code Phone} matches any of the keywords given.
 */
public class KinPhoneContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public KinPhoneContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public KinPhoneContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringCaseSensitive(patient.getNextOfKin().getPhone()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(patient.getNextOfKin().getPhone()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringCaseSensitive(patient.getNextOfKin().getPhone()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(patient.getNextOfKin().getPhone()
                    .toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof KinPhoneContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((KinPhoneContainsKeywordsPredicate) other).keywords)); // state check
    }
}
