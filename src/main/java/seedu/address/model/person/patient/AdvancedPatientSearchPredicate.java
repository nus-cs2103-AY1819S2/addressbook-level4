/* @@author wayneswq */
package seedu.address.model.person.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Patient}'s {@code Name} matches any of the keywords given.
 */
public class AdvancedPatientSearchPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public AdvancedPatientSearchPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient patient) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsSubwordOrQuotedFullwordIgnoreCase(
                        patient.toAdvancedSearchString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AdvancedPatientSearchPredicate // instanceof handles nulls
                && keywords.equals(((AdvancedPatientSearchPredicate) other).keywords)); // state check
    }

}
