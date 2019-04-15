package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s attached {@code NextOfKin} {@code Name} matches any of the keywords given.
 */
public class KinAddressContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public KinAddressContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public KinAddressContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getAddress()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getAddress()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getAddress()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getAddress()
                    .toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof KinAddressContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((KinAddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}

