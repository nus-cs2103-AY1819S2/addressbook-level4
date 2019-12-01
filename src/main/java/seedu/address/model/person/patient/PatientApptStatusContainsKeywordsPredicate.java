/* @@author wayneswq */
package seedu.address.model.person.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Patient}'s {@code Name} matches any of the keywords given.
 */
public class PatientApptStatusContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public PatientApptStatusContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient patient) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        patient.getAppointmentStatus().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientApptStatusContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PatientApptStatusContainsKeywordsPredicate) other).keywords)); // state check
    }

}
