package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;


/**
 * Tests that a {@code Patient}'s {@code DateOfBirth} matches any of the keywords given.
 */
public class DateOfBirthContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public DateOfBirthContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public DateOfBirthContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getDateOfBirth().getDate(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getDateOfBirth().getDate(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getDateOfBirth().getDate(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getDateOfBirth().getDate(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateOfBirthContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((DateOfBirthContainsKeywordsPredicate) other).keywords)); // state check
    }
}
